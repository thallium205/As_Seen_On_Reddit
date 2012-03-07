package russell.john.server.handler;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import russell.john.server.dao.LogDAO;
import russell.john.server.dao.SettingsDAO;
import russell.john.server.domain.UserLog;
import russell.john.server.domain.UserSettings;
import russell.john.server.utils.Util;
import russell.john.shared.action.SetSettingsAction;
import russell.john.shared.action.SetSettingsResult;
import russell.john.shared.utils.LinkUtils;

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
			setUserSettingsAndTroll(action);
		}

		catch (JSONException e)
		{
			throw new ActionException("The feed returned unexpected data.", e);
		}

		catch (ParseException e)
		{
			throw new ActionException("The feed returned a weird date.", e);
		}

		catch (IOException e)
		{
			throw new ActionException(e);
		}

		// We don't care because we don't return anything after a user sets
		// their settings
		return new SetSettingsResult();
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
	 * @throws JSONException
	 * @throws ParseException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private void setUserSettingsAndTroll(final SetSettingsAction action) throws JSONException, ParseException, IOException
	{
		// Save the user to the datastore, and also get the user for authtoken
		SettingsDAO settingsDao = new SettingsDAO();
		UserSettings settings = settingsDao.ofy().query(UserSettings.class).filter("fbId", action.getFbId()).get();

		settings.setComment(action.getComment());
		settings.setFriends(action.getFriends());

		settingsDao.put(settings);

		// Get the user's feed
		JSONArray feed = new JSONObject(Util.fetchUrl(LinkUtils.getFeedListUrl(settings.getAuthToken()))).getJSONArray("data");
		for (int i = 0; i < feed.length(); i++)
		{
			JSONObject feedItem = feed.getJSONObject(i);
			// We are only interested in links
			if (feedItem.getString("type").contains("link"))
			{
				// Check to make sure the feed item happened after the last time
				// the user fired the event
				
				
				Date post = Util.GetDateFromUTCString(feedItem.getString("created_time"));
				Date lastChecked = settings.getLastCheckedDate();
				
				if (Util.GetDateFromUTCString(feedItem.getString("created_time")).after(settings.getLastCheckedDate()))
				{
					// Check user specific list
					Boolean userMatch = false;
					if (settings.getFriends() == null || settings.getFriends().isEmpty())
						userMatch = true;
					for (Iterator<String> iter = action.getFriends().iterator(); iter.hasNext();)
						if (feedItem.getJSONObject("from").getString("id").contains(iter.next()))
							userMatch = true;
					if (userMatch)
					{
						// Get the url from reddit.
						ArrayList<Integer> scores = new ArrayList<Integer>();
						JSONArray redditItems = new JSONObject(Util.fetchUrl(LinkUtils.getRedditSearchUrl(feedItem.getString("link"))))
								.getJSONObject("data").getJSONArray("children");
						for (int j = 0; j < redditItems.length(); j++)
						{
							JSONObject item = redditItems.getJSONObject(j).getJSONObject("data");
							scores.add(item.getInt("score"));
						}

						// Sort the list desc
						Collections.sort(scores);
						Collections.reverse(scores);

						// Find the JSONObject with this score and get its link
						for (int j = 0; j < redditItems.length(); j++)
						{
							JSONObject redditItem = redditItems.getJSONObject(j).getJSONObject("data");
							if (redditItem.getInt("score") == scores.get(0))
							{
								// Get the reddit permalink
								String redditPermalink = "http://www.reddit.com" + redditItem.getString("permalink");

								// Make a post on the user's link comment
								String fbComment = action.getComment() + " " + redditPermalink;
								Util.Post(LinkUtils.getPostCommentUrl(settings.getAuthToken(), feedItem.getString("id")), "message", fbComment);

								// Store the log in the database
								LogDAO logDao = new LogDAO();
								UserLog log = new UserLog();
								log.setFbId(action.getFbId());
								log.setFbVictimId(feedItem.getJSONObject("from").getString("id"));
								log.setFbComment(fbComment);
								log.setFbCommentPermalink("http://facebook.com/" + feedItem.getString("id").split("_")[0] + "/posts/"
										+ feedItem.getString("id").split("_")[1]);
								log.setDate(Util.GetDate());
								logDao.put(log);
								
								// Store the new timestamp from the usersettings in the database
								settings.setLastCheckedDate(Util.GetDate());
								settingsDao.put(settings);
							}
						}
					}
				}
			}
		}
	}
}
