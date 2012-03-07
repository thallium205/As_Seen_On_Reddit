package russell.john.client.view;

import russell.john.client.presenter.LogPresenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * Lets the user know how many times the application has trolled their friends.
 * @author John
 *
 */
public class LogView extends ViewImpl implements LogPresenter.MyView
{

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, LogView>
	{
	}

	@Inject
	public LogView(final Binder binder)
	{
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget()
	{
		return widget;
	}
}
