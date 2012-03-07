package russell.john.client.view;

import russell.john.client.presenter.MainPresenter;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * The base view that allows child views the ability to reveal themselves in the slots.
 * @author John
 *
 */
public class MainView extends ViewImpl implements MainPresenter.MyView
{

	private final Widget widget;
	
	@UiField
	HTMLPanel mainContentPanel;
	@UiField 
	HTMLPanel sideContentPanel;

	public interface Binder extends UiBinder<Widget, MainView>
	{
	}

	@Inject
	public MainView(final Binder binder)
	{
		widget = binder.createAndBindUi(this);
	}
	
	public void setInSlot(Object slot, Widget content)
	{
		if (slot == MainPresenter.TYPE_SetMainContent)
		{			
			setMainContent(content);
		}
		
		else if (slot == MainPresenter.TYPE_SetSideContent)
		{
			setSideContent(content);
		}
		
		else
		{
			super.setInSlot(slot, content);
		}
	}
	
	private void setMainContent(Widget content) 
	{
	    mainContentPanel.clear();
	    if (content != null) 
	    {
	      mainContentPanel.add(content);
	    }
	}
	
	private void setSideContent(Widget content)
	{
		sideContentPanel.clear();
		if (content != null)
		{
			sideContentPanel.add(content);
		}		
	}

	@Override
	public Widget asWidget()
	{
		return widget;
	}
}
