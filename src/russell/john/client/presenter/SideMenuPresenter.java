package russell.john.client.presenter;

import russell.john.client.event.FacebookResultReceivedEvent;
import russell.john.client.event.FacebookResultReceivedEvent.FacebookResultReceivedHandler;
import russell.john.client.place.NameTokens;
import russell.john.client.view.SideMenuUiHandlers;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;

/**
 * The control class of the side menu.
 * @author John
 *
 */
public class SideMenuPresenter extends PresenterWidget<SideMenuPresenter.MyView> implements SideMenuUiHandlers, FacebookResultReceivedHandler
{

	public interface MyView extends View, HasUiHandlers<SideMenuUiHandlers>
	{
		// TODO Put your view methods here
	}
	
	@ProxyEvent
	@Override
	public void onFacebookResultReceived(FacebookResultReceivedEvent event)
	{
		
		
	}
	
	PlaceManager placeManager;

	@Inject
	public SideMenuPresenter(final EventBus eventBus, final MyView view, final PlaceManager placeManager)
	{
		super(eventBus, view);
		getView().setUiHandlers(this);
		
		this.placeManager = placeManager;
	}

	@Override
	protected void onBind()
	{
		super.onBind();
	}

	@Override
	public void onBtnSettingsClicked()
	{
		PlaceRequest placeRequest = new PlaceRequest(NameTokens.settings);				
		placeManager.revealPlace(placeRequest);		
		
	}

	@Override
	public void onBtnLogClicked()
	{
		PlaceRequest placeRequest = new PlaceRequest(NameTokens.log);				
		placeManager.revealPlace(placeRequest);		
		
	}

	
}
