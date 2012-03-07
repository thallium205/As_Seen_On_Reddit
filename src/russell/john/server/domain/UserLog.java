package russell.john.server.domain;

import java.util.Date;

import javax.persistence.Id;

public class UserLog
{
	@Id
	Long userId; // An internal id used by objectify
	
	String fbId; // The user who started the troll	
	String fbVictimId; // The user who was trolled
	String fbCommentPermalink; // The facebook comment permalink
	String fbComment; // What the user actually said to the victim
	Date date; // The local time at which it happened.
	
	public UserLog()
	{
		
	}

	public String getFbId()
	{
		return fbId;
	}

	public void setFbId(String fbId)
	{
		this.fbId = fbId;
	}

	public String getFbVictimId()
	{
		return fbVictimId;
	}

	public void setFbVictimId(String fbVictimId)
	{
		this.fbVictimId = fbVictimId;
	}

	public String getFbCommentPermalink()
	{
		return fbCommentPermalink;
	}

	public void setFbCommentPermalink(String fbCommentPermalink)
	{
		this.fbCommentPermalink = fbCommentPermalink;
	}

	public String getFbComment()
	{
		return fbComment;
	}

	public void setFbComment(String fbComment)
	{
		this.fbComment = fbComment;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}	
	
}
