package russell.john.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import russell.john.shared.action.FacebookResult;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * The received event on the event bus which notifies interested presenters that facebook data has been successfully retrieved.
 * @author John
 *
 */
public class FacebookResultReceivedEvent extends GwtEvent<FacebookResultReceivedEvent.FacebookResultReceivedHandler>
{

	public static Type<FacebookResultReceivedHandler> TYPE = new Type<FacebookResultReceivedHandler>();
	private FacebookResult fbResult;

	public interface FacebookResultReceivedHandler extends EventHandler
	{
		public void onFacebookResultReceived(FacebookResultReceivedEvent event);
	}

	public interface FacebookResultReceivedHasHandlers extends HasHandlers
	{
		public HandlerRegistration addFacebookResultReceivedHandler(FacebookResultReceivedHandler handler);
	}

	public FacebookResultReceivedEvent(FacebookResult fbResult)
	{
		this.fbResult = fbResult;
	}

	public FacebookResult getFbResult()
	{
		return fbResult;
	}

	@Override
	protected void dispatch(FacebookResultReceivedHandler handler)
	{
		handler.onFacebookResultReceived(this);
	}

	@Override
	public Type<FacebookResultReceivedHandler> getAssociatedType()
	{
		return TYPE;
	}

	public static Type<FacebookResultReceivedHandler> getType()
	{
		return TYPE;
	}

	public static void fire(HasHandlers source, FacebookResult fbResult)
	{
		source.fireEvent(new FacebookResultReceivedEvent(fbResult));
	}
}
