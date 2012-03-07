package russell.john.shared.action;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

/**
 * Represents a friend.  This is displayed in the data grid.
 * @author John
 *
 */
public class FacebookFriendType implements Serializable
{
	private static final long serialVersionUID = 3385735668711332250L;
	private String id;
	private String name;

	protected FacebookFriendType()
	{

	}

	public FacebookFriendType(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public static final ProvidesKey<FacebookFriendType> KEY_PROVIDER = new ProvidesKey<FacebookFriendType>()
	{
		public Object getKey(FacebookFriendType item)
		{
			return item == null ? null : item.getId();
		}
	};

}
