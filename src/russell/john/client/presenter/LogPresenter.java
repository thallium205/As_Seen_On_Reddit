package russell.john.client.presenter;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import russell.john.client.place.NameTokens;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * The log presenter that reveals log information stored at the server.
 * @author John
 *
 */
public class LogPresenter extends Presenter<LogPresenter.MyView, LogPresenter.MyProxy>
{

	public interface MyView extends View
	{
		// TODO Put your view methods here
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.log)
	public interface MyProxy extends ProxyPlace<LogPresenter>
	{
	}

	@Inject
	public LogPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy)
	{
		super(eventBus, view, proxy);
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
}
