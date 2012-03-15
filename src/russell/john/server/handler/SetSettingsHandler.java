package russell.john.server.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import russell.john.server.dao.LogDAO;
import russell.john.server.dao.SettingsDAO;
import russell.john.server.domain.UserLog;
import russell.john.server.domain.UserSettings;
import russell.john.server.utils.Util;
import russell.john.shared.action.SetSettingsAction;
import russell.john.shared.action.SetSettingsResult;
import russell.john.shared.utils.LinkUtils;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * 
 * The main action to perform. This handler will save a user's settings and
 * commit it to the datastore. A user will always exist because
 * GetSettingsResult will generate a user if one does not exist.
 * 
 * Then, it will scan a user's feed and check their friend's links against
 * reddit. If there is a result, it will post on the link that it has been on
 * reddit already.
 * 
 * 1) Get all the users out of the datastore. Use an iterable to prevent high
 * memory consumption 2) Get the user's feed and pull out all posted link while
 * timestamp has not been surpassed from the record in the database and will
 * skip the user himself if he posts a link (ie: look through everything until
 * we've already checked all previous records) 3)For each link, search against
 * reddit and sort by what the user wanted. 4) Post in the comment section of
 * the comment 5) Done
 * 
 * @author John
 * 
 */
public class SetSettingsHandler implements ActionHandler<SetSettingsAction, SetSettingsResult>
{
	// private Provider<HttpServletRequest> requestProvider;
	private static final Logger LOG = Logger.getLogger(SetSettingsHandler.class.getName());

	/**
	 * Injection to get the request provider. Needed in certain situations.
	 * 
	 * @param servletContext
	 * @param requestProvider
	 */
	@Inject
	SetSettingsHandler(final ServletContext servletContext, final Provider<HttpServletRequest> requestProvider)
	{
		// this.requestProvider = requestProvider;
	}

	@Override
	public SetSettingsResult execute(final SetSettingsAction action, final ExecutionContext context) throws ActionException
	{

		try
		{
			return setUserSettingsAndTroll(action);
		}

		catch (Exception e)
		{
			throw new ActionException(e);
		}

	}

	/**
	 * The class type.
	 */
	@Override
	public Class<SetSettingsAction> getActionType()
	{
		return SetSettingsAction.class;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.gwtplatform.dispatch.shared.Action,
	 *      com.gwtplatform.dispatch.shared.Result,
	 *      com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(SetSettingsAction action, SetSettingsResult result, ExecutionContext context) throws ActionException
	{
	}

	/**
	 * Saves the user's settings before begining main troll operation
	 * 
	 * @param action
	 * @throws Exception
	 */
	private SetSettingsResult setUserSettingsAndTroll(final SetSettingsAction action) throws Exception
	{
		// Get th user's auth token
		SettingsDAO settingsDao = new SettingsDAO();
		UserSettings settings = settingsDao.ofy().query(UserSettings.class).filter("fbId", action.getFbId()).get();
		settings.setComment(action.getComment());
		settings.setRedditThreshold(action.getRedditThreshold());
		settings.setFriends(action.getFriends());

		// The amount of times we posted on someone's wall
		int match = 0;

		// A collection of all the reddit search URLs. This is needed to do an
		// async call since the reddit search latency is
		// http://tinyurl.com/3hfn7vf
		ArrayList<String> redditUrls = new ArrayList<String>();
		// A collection of all the user's feed items. This is needed once the
		// url futures return back
		ArrayList<JSONObject> fbFeedItems = new ArrayList<JSONObject>();

		// Get the user's feed
		JSONArray feed = new JSONObject(Util.fetchUrl(LinkUtils.getFeedListUrl(settings.getAuthToken()))).getJSONArray("data");
		for (int i = 0; i < feed.length(); i++)
		{
			JSONObject feedItem = feed.getJSONObject(i);
			// We are only interested in links
			if (feedItem.getString("type").contains("link") || feedItem.getString("type").contains("video"))
			{
				// Check to make sure the feed item happened after the last time
				// the user fired the event
				if (Util.GetGMTDateFromUTCString(feedItem.getString("created_time")).after(settings.getLastCheckedDate()))
				{
					// Check user specific list
					Boolean userMatch = false;
					if (settings.getFriends() == null || settings.getFriends().isEmpty())
						userMatch = true;
					for (Iterator<String> iter = action.getFriends().iterator(); iter.hasNext();)
					{
						if (feedItem.getJSONObject("from").getString("id").contains(iter.next()))
							userMatch = true;
					}

					if (userMatch)
					{
						// Not all type: video have a link
						if (feedItem.has("link"))
						{
							// Because reddit's search is horrible, we are going
							// to
							// do an async pull from their api.
							redditUrls.add(LinkUtils.getRedditSearchUrl(feedItem.getString("link")));

							// We add this feed item to this arraylist so that
							// when
							// the spawned url futures return, we can find it
							// based
							// upon the url and post the witty response to the
							// correct comment
							fbFeedItems.add(feedItem);
						}
					}
				}
			}
		}

		// Process all the collected reddit urls at once. Google will let us do
		// a max of 10, so if there are more than 10 in this arraylist we will
		// stop. This is the limitation of the app due to reddit. We will give
		// reddit 25 seconds to perform all the searches.
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		FetchOptions fetchOptions = FetchOptions.Builder.withDeadline(500).followRedirects();
		ArrayList<Future<HTTPResponse>> asyncRedditResponses = new ArrayList<Future<HTTPResponse>>();
		for (int i = 0; i < redditUrls.size(); i++)
		{
			HTTPRequest request = new HTTPRequest(new URL("http://ineedaride.mobi/reddit.php?redditUrl=" + redditUrls.get(i)), HTTPMethod.GET,
					fetchOptions);
			asyncRedditResponses.add(fetcher.fetchAsync(request));
			// Must obey API rules. Otherwise, banhammer.
			Thread.sleep(1000);
		}

		// We now wait for all 10 to come (hopefully) pouring in at once
		for (Future<HTTPResponse> future : asyncRedditResponses)
		{
			HTTPResponse response;

			// We are giving reddit 25 seconds to perform all the search
			// operations. The maximum is 30, with 5 seconds reserved for
			// facebook and server overhead.
			response = future.get(); // We wait now.
			JSONObject redditObject = new JSONObject(new String(response.getContent()));
			JSONArray redditSearchResult;
			if (redditObject.has("error"))
			{
				LOG.severe(redditObject.toString());
				throw new Exception("Reddit error for user: " + settings.getUserId());
			}

			else
			{
				redditSearchResult = redditObject.getJSONObject("data").getJSONArray("children");
			}

			ArrayList<Integer> scores = new ArrayList<Integer>();
			for (int i = 0; i < redditSearchResult.length(); i++)
			{
				JSONObject item = redditSearchResult.getJSONObject(i).getJSONObject("data");
				scores.add(item.getInt("score"));
			}

			// Sort the list of scores desc
			Collections.sort(scores);
			Collections.reverse(scores);

			// Find the JSONObject with this score and get its link
			for (Iterator<JSONObject> iter = fbFeedItems.iterator(); iter.hasNext();)
			{
				JSONObject fbFeedItem = iter.next();
				for (int i = 0; i < redditSearchResult.length(); i++)
				{
					JSONObject redditItem = redditSearchResult.getJSONObject(i).getJSONObject("data");

					if (redditItem.getInt("score") == scores.get(0) && fbFeedItem.getString("link").contains(redditItem.getString("url")))
					{
						// We only want to consider reddit scores above the user selected value
						if (redditItem.getInt("score") > action.getRedditThreshold())
						{
							// We found the match. Comment it to the person's
							// link post. This needs to be done
							// asynchronously/ASAP in order to benefit from the
							// async call of reddit.

							// Get the reddit permalink
							String redditPermalink = "http://www.reddit.com" + redditItem.getString("permalink");

							// Post it to the user's wall asynchronously
							String fbComment = action.getComment() + " " + redditPermalink;

							// Get a new fetcher reference
							fetcher.fetchAsync(Util.AsyncPost(LinkUtils.getPostCommentUrl(settings.getAuthToken(), fbFeedItem.getString("id")),
									"message", fbComment));

							// Store the log in the database asynchronously
							LogDAO logDao = new LogDAO();
							UserLog log = new UserLog();
							log.setFbId(action.getFbId());
							log.setFbVictimId(fbFeedItem.getJSONObject("from").getString("id"));
							log.setFbComment(fbComment);
							log.setFbCommentPermalink("http://facebook.com/" + fbFeedItem.getString("id").split("_")[0] + "/posts/"
									+ fbFeedItem.getString("id").split("_")[1]);
							log.setDate(Util.GetDate());
							logDao.ofy().async().put(log);

							// Increment the times we found a positive match.
							// this
							// is for the user
							match++;

							// Break out of the loop to prevent double posting.
							// This happens if the two friends post the same
							// link.
							break;
						}
					}
				}
			}
		}

		// Store the new timestamp from the usersettings in the database
		settings.setLastCheckedDate(Util.GetDate());
		settingsDao.put(settings);

		return new SetSettingsResult(redditUrls, match);
	}
}
