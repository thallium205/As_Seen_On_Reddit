package russell.john.client.view;

import russell.john.client.presenter.SideMenuPresenter;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.google.inject.Inject;

/**
 * The side menu view holds all the menu options that a user can go to.  Right now there are two: the main page and the log page.
 * @author John
 *
 */
public class SideMenuView extends ViewWithUiHandlers<SideMenuUiHandlers> implements SideMenuPresenter.MyView
{
	@UiField 
	TextButton btnSettings;
	@UiField 
	TextButton btnLog;
	@UiField 
	HTMLPanel panel;

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SideMenuView>
	{
	}

	@Inject
	public SideMenuView(final Binder binder)
	{
		widget = binder.createAndBindUi(this);
	}
	
	@UiHandler("btnSettings")
	void onBtnProfileClick(ClickEvent event) 
	{
		if (getUiHandlers() != null)
		{
			getUiHandlers().onBtnSettingsClicked();
		}	
	}
	
	@UiHandler("btnLog")
	void onBtnTribesClick(ClickEvent event) 
	{
		if (getUiHandlers() != null)
		{
			getUiHandlers().onBtnLogClicked();
		}	
	}

	@Override
	public Widget asWidget()
	{
		return widget;
	}
}
