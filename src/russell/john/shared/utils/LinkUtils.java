package russell.john.shared.utils;

/**
 * This utility generates relevant facebook url's to get tokens and auth codes to authenticate with facebook api.
 * @author John
 *
 */
public class LinkUtils
{
	private static final String SECRET = "3e2aa555609361ef1dd055675fdd69a0"; 
	private static final String APPLICATION_ID = "307919755941311"; 

	private static final String FB_GRAPH_URL = "https://graph.facebook.com/";
	private static final String FB_OAUTH_URL = FB_GRAPH_URL + "oauth/";
	
	private static final String FB_ME_URL = FB_GRAPH_URL + "me?";
	private static final String FB_FRIENDS_URL = FB_GRAPH_URL + "me/friends?";
	private static final String FB_FEED = FB_GRAPH_URL + "me/home?";
	
	private static final String REDDIT_URL = "http://www.reddit.com/api/info.json?url=";

	// localhost is for testing
	// private static final String REDIRECT_URL = "http://127.0.0.1:8888/As_Seen_On_Reddit.html?gwt.codesvr=127.0.0.1:9997/";
	//private static final String REDIRECT_URL = "http://127.0.0.1:8888/";
	private static final String REDIRECT_URL = "http://asseenonreddit.appspot.com/";
	
	/**
	 * The application ID needed for generating authentication.
	 * @return The application ID.
	 */
	public static String getApplicationId()
	{
		return APPLICATION_ID;
	}

	/**]
	 * The secret application specific API code.
	 * @return The secret ID.
	 */
	public static String getSecret()
	{
		return SECRET;
	}

	/**
	 * Builds a url that returns an authorization code given the permissions that need to be build.
	 * @return The URL to fetch an authorization key.
	 */
	public static String getAuthorizeUrl()
	{
		final StringBuilder sb = new StringBuilder(FB_OAUTH_URL);
		sb.append("authorize?client_id=").append(APPLICATION_ID);
		sb.append("&display=page&redirect_uri=").append(REDIRECT_URL);
		sb.append("&scope=read_stream,publish_stream");
		return sb.toString();
	}

	/**
	 * Builds a url that will return an access token.  This allows the application the ability to get elevated permissions.
	 * @param authCode - The auhorization code generated from getAuthorizeUrl()
	 * @return The URL that can fetch a token that must be passed with all elevated API calls.
	 */
	public static String getAccessTokenUrl(final String authCode)
	{
		final StringBuilder sb = new StringBuilder(FB_OAUTH_URL);
		sb.append("access_token?canvas=1&fbconnect=0&type=user_agent&client_id=").append(APPLICATION_ID);
		sb.append("&redirect_uri=").append(REDIRECT_URL);
		sb.append("&client_secret=").append(SECRET);
		sb.append("&code=").append(authCode);
		return sb.toString();
	}
	
	/**
	 * Gets a URL that can fetch public information about the user.  This application does not have permission to get detailed info about the user, but it is enough to get the ID, name, and other publically available info.
	 * @param authToken Probably not needed, but required for this call.
	 * @return The URL to get basic user information
	 */
	public static String getMeUrl(final String authToken)
	{
		return FB_ME_URL + authToken;
	}

	/**
	 * Gets a URL that can fetch a user's friends list.
	 * @param authToken Needed since this is an elevated API call.
	 * @return The URL to get a user's complete friend list.
	 */
	public static String getFriendsListUrl(final String authToken)
	{
		return FB_FRIENDS_URL + authToken;
	}
	
	/**
	 * Gets a URL that will return the user's feed
	 * @param authToken
	 * @return
	 */
	public static String getFeedListUrl(final String authToken)
	{
		return FB_FEED + authToken;
	}
	
	/**
	 * Gets a URL that will return a search query of reddit
	 * @param link
	 * @return
	 */
	public static String getRedditSearchUrl(final String link)
	{
		return REDDIT_URL + link;
	}
	
	/**
	 * Gets a URL that requires an HTTP Post parameter with it to post a comment to a facebook item.
	 * @param postId - The ID of the post
	 * @return
	 */
	public static String getPostCommentUrl(String authToken, String postId)
	{
		return FB_GRAPH_URL + postId + "/comments?" + authToken;
	}
}