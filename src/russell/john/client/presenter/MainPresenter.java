package russell.john.client.presenter;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.NameToken;

import russell.john.client.event.FacebookResultReceivedEvent;
import russell.john.client.place.NameTokens;
import russell.john.shared.action.FacebookAction;
import russell.john.shared.action.FacebookResult;
import russell.john.shared.utils.LinkUtils;

import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * The base presenter where it authenticates with facebook to get an auth token.
 * @author John
 *
 */
public class MainPresenter extends Presenter<MainPresenter.MyView, MainPresenter.MyProxy>
{

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetSideContent = new Type<RevealContentHandler<?>>();

	SettingsPresenter settingsPresenter;
	SideMenuPresenter sideMenuPresenter;

	public interface MyView extends View
	{
		// TODO Put your view methods here
	}

	@ProxyStandard
	@NameToken(NameTokens.main)
	public interface MyProxy extends ProxyPlace<MainPresenter>
	{
	}

	@Inject
	public MainPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DispatchAsync dispatcher, final SideMenuPresenter side)
	{
		super(eventBus, view, proxy);
		
		this.sideMenuPresenter = side;
		
		// We need to authenticate with facebook
		if (Window.Location.getParameter("code") != null)
		{
			// Do facebook action
			dispatcher.execute(new FacebookAction(Window.Location.getParameter("code")), new AsyncCallback<FacebookResult>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(FacebookResult result)
				{					
					// Fire the event bus, which will force reveal settings presenter
					FacebookResultReceivedEvent.fire(eventBus, result);				
				}
			});
		}

		else
		{
			Window.open(LinkUtils.getAuthorizeUrl(), "_self", "");
		}
	}

	@Override
	protected void revealInParent()
	{
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	protected void onReveal()
	{
		super.onReveal();

		// Load the settings main view by default
		setInSlot(TYPE_SetMainContent, settingsPresenter);

		// Load the profile side view
		setInSlot(TYPE_SetSideContent, sideMenuPresenter);
	}

	@Override
	protected void onBind()
	{
		super.onBind();
	}
}
