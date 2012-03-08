package russell.john.server.handler;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import russell.john.server.utils.Util;
import russell.john.shared.action.FacebookAction;
import russell.john.shared.action.FacebookFriendType;
import russell.john.shared.action.FacebookResult;
import russell.john.shared.utils.LinkUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * 1) User clicks a link on your site to Facebook.getLoginRedirectURL() 2)
 * Facebook asks them for their username/password to log in to your application
 * 3) Assuming they authenticate with Facebook, Facebook then redirects the user
 * to your “redirect_uri” with a parameter “code” passed along. 4) You use the
 * “code” parameter to query the Facebook authentication service –
 * Facebook.getAuthURL(request.getParameter(“code”)) 5) Assuming it was a valid
 * authentication code, Facebook will pass you back an “access_token” that you
 * can use to access the Facebook Graph API for the given user.
 */
public class FacebookHandler implements ActionHandler<FacebookAction, FacebookResult>
{
	// private Provider<HttpServletRequest> requestProvider;

	@Inject
	FacebookHandler(final ServletContext servletContext, final Provider<HttpServletRequest> requestProvider)
	{
		// this.requestProvider = requestProvider;
	}

	@Override
	public FacebookResult execute(final FacebookAction action, final ExecutionContext context) throws ActionException
	{
		String fbId;
		String fbName;
		ArrayList<FacebookFriendType> fbFriends = new ArrayList<FacebookFriendType>();
		// We need to get an auth token from facebook
		final String url = LinkUtils.getAccessTokenUrl(action.getAuthCode());
		final String authToken = Util.fetchUrl(url);

		try
		{
			// Get the users info
			JSONObject me = new JSONObject(Util.fetchUrl(LinkUtils.getMeUrl(authToken)));
			fbId = me.getString("id");
			fbName = me.getString("name");

			// Get the users friends
			JSONArray data = new JSONObject(Util.fetchUrl(LinkUtils.getFriendsListUrl(authToken))).getJSONArray("data");
			for (int i = 0; i < data.length(); i++)
				fbFriends.add(new FacebookFriendType(data.getJSONObject(i).getString("id"), data.getJSONObject(i).getString("name")));
		}

		catch (JSONException e)
		{
			e.printStackTrace();
			throw new ActionException(url, e);
		}

		// Store

		return new FacebookResult(authToken, fbId, fbName, fbFriends);
	}

	@Override
	public Class<FacebookAction> getActionType()
	{
		return FacebookAction.class;
	}

	@Override
	public void undo(FacebookAction action, FacebookResult result, ExecutionContext context) throws ActionException
	{
	}
}
