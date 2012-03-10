package russell.john.client.presenter;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;

import russell.john.client.event.FacebookResultReceivedEvent;
import russell.john.client.event.FacebookResultReceivedEvent.FacebookResultReceivedHandler;
import russell.john.client.place.NameTokens;
import russell.john.shared.action.GetLogAction;
import russell.john.shared.action.GetLogResult;

import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * The log presenter that reveals log information stored at the server.
 * 
 * @author John
 * 
 */
public class LogPresenter extends Presenter<LogPresenter.MyView, LogPresenter.MyProxy> implements FacebookResultReceivedHandler
{
	final DispatchAsync dispatcher;
	FacebookResultReceivedEvent fbResults;

	public interface MyView extends View
	{
		public void setLogInfo(GetLogResult result);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.log)
	public interface MyProxy extends ProxyPlace<LogPresenter>
	{
	}

	@ProxyEvent
	@Override
	public void onFacebookResultReceived(FacebookResultReceivedEvent event)
	{
		this.fbResults = event;
	}

	@Inject
	public LogPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DispatchAsync dispatcher)
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
	
	@Override
	protected void onReset()
	{
		super.onReset();
		// Download the log data from the server and display them
		if (fbResults != null)
			downloadAndLoadLogs();
	}
	

	private void downloadAndLoadLogs()
	{
		dispatcher.execute(new GetLogAction(fbResults.getFbResult().getFbId()), new AsyncCallback<GetLogResult>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(GetLogResult result)
			{
				if (result != null)
					getView().setLogInfo(result);
			}
		});
	}
}
