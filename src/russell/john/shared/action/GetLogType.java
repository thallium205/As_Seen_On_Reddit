package russell.john.shared.action;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetLogType implements IsSerializable
{
	String fbVictimName;
	String fbCommentPermalink;
	String fbComment;
	Date date; 
	
	protected GetLogType()
	{
		
	}
	
	public GetLogType(String fbVictimName, String fbCommentPermalink, String fbComment, Date date)
	{
		this.fbVictimName = fbVictimName;
		this.fbCommentPermalink = fbCommentPermalink;
		this.fbComment = fbComment;
		this.date = date;
	}

	public String getFbVictimName()
	{
		return fbVictimName;
	}

	public void setFbVictimName(String fbVictimName)
	{
		this.fbVictimName = fbVictimName;
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
