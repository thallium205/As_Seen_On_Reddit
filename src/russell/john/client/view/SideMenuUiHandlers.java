package russell.john.client.view;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * An interface that specifies what the sidemenu can do and how the presenter responds to the views handlers.
 * @author John
 *
 */
public interface SideMenuUiHandlers extends UiHandlers
{
	void onBtnSettingsClicked();
	void onBtnLogClicked();
}
