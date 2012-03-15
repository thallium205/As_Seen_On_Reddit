package russell.john.server.domain;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Id;

/**
 * The main settings object that records the person's personal settings.
 * @author John
 *
 */
public class UserSettings
{	
	@Id
	Long userId; // An internal id used by objectify
	
	String fbId;
	Integer redditThreshold;
	String comment;
	Date lastCheckedDate;
	ArrayList<String> friends;
	String authToken; // An auth token
	
	public UserSettings()
	{
		// serialization		
	}
	
	public UserSettings(String fbId, Integer redditThreshold, String comment, Date lastCheckedDate, ArrayList<String> friends, String authToken)
	{
		this.fbId = fbId;
		this.redditThreshold = redditThreshold;
		this.comment = comment;
		this.lastCheckedDate = lastCheckedDate;
		this.friends = friends;
		this.authToken = authToken;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getFbId()
	{
		return fbId;
	}

	public void setFbId(String fbId)
	{
		this.fbId = fbId;
	}		

	public Integer getRedditThreshold()
	{
		return redditThreshold;
	}

	public void setRedditThreshold(Integer redditThreshold)
	{
		this.redditThreshold = redditThreshold;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Date getLastCheckedDate()
	{
		return lastCheckedDate;
	}

	public void setLastCheckedDate(Date lastCheckedDate)
	{
		this.lastCheckedDate = lastCheckedDate;
	}

	public ArrayList<String> getFriends()
	{
		return friends;
	}

	public void setFriends(ArrayList<String> friends)
	{
		this.friends = friends;
	}

	public String getAuthToken()
	{
		return authToken;
	}

	public void setAuthToken(String authToken)
	{
		this.authToken = authToken;
	}
	
	
}
