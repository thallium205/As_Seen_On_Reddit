package russell.john.client.presenter;

import java.util.ArrayList;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;

import russell.john.client.event.FacebookResultReceivedEvent;
import russell.john.client.event.FacebookResultReceivedEvent.FacebookResultReceivedHandler;
import russell.john.client.place.NameTokens;
import russell.john.client.view.SettingsUiHandlers;
import russell.john.shared.action.FacebookResult;
import russell.john.shared.action.GetSettingsAction;
import russell.john.shared.action.GetSettingsResult;
import russell.john.shared.action.SetSettingsAction;
import russell.john.shared.action.SetSettingsResult;

import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The control class of the main presenter view
 * @author John
 *
 */
public class SettingsPresenter extends Presenter<SettingsPresenter.MyView, SettingsPresenter.MyProxy> implements SettingsUiHandlers,
		FacebookResultReceivedHandler
{
	FacebookResultReceivedEvent fbResultEvent;
	final DispatchAsync dispatcher;

	public interface MyView extends View, HasUiHandlers<SettingsUiHandlers>
	{
		// Called when facebook info has been received from the server.
		public void setFacebookInfo(FacebookResult result);
		
		// Called when settings info has been received from the server
		public void setSettingsInfo(GetSettingsResult result);

		// The presenter asks the view to get the custom text the user wants.
		public String getUserText();

		// The presenter asks the veiw to get all the selected users to troll.
		public ArrayList<String> getSelectedUsers();

		// Called when the server has returned something
		public void setApplyConfirm(String text);
	}

	@ProxyEvent
	@Override
	public void onFacebookResultReceived(FacebookResultReceivedEvent event)
	{		
		fbResultEvent = event;
		
		getView().setUiHandlers(this);
		
		getView().setFacebookInfo(event.getFbResult());

		getPersonalSettings();	
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.settings)
	public interface MyProxy extends ProxyPlace<SettingsPresenter>
	{
	}

	@Inject
	public SettingsPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DispatchAsync dispatcher)
	{
		super(eventBus, view, proxy);

		this.dispatcher = dispatcher;
		
	}

	@Override
	protected void revealInParent()
	{
		RevealContentEvent.fire(this, MainPresenter.TYPE_SetMainContent, this);
	}

	@Override
	protected void onBind()
	{
		super.onBind();
	}

	// Calls the server to get the user's settings. IF they dont exist, it will
	// create one in the database and return the default values.

	private void getPersonalSettings()
	{
		dispatcher.execute(new GetSettingsAction(fbResultEvent.getFbResult().getFbId(), fbResultEvent.getFbResult().getAuthToken()), new AsyncCallback<GetSettingsResult>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				getView().setApplyConfirm("Reddit barfed.");
			}

			@Override
			public void onSuccess(GetSettingsResult result)
			{
				getView().setSettingsInfo(result);
				forceReveal();
			}
		});
	}

	@Override
	public void onBtnApply()
	{
		// Sets the settings.
		dispatcher.execute(new SetSettingsAction(fbResultEvent.getFbResult().getFbId(), getView().getUserText(), getView().getSelectedUsers()), new AsyncCallback<SetSettingsResult>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				getView().setApplyConfirm("It looks like Reddit barfed!  Try again.");
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(SetSettingsResult result)
			{
				// Notify the user that their settings have been changed
				getView().setApplyConfirm("Success!  View the logs to see who got hit.");

			}
		});
	}
}
