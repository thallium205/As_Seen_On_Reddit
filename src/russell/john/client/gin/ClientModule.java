package russell.john.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import russell.john.client.place.ClientPlaceManager;
import russell.john.client.place.DefaultPlace;
import russell.john.client.place.NameTokens;
import russell.john.client.presenter.LogPresenter;
import russell.john.client.presenter.MainPresenter;
import russell.john.client.presenter.SettingsPresenter;
import russell.john.client.presenter.SideMenuPresenter;
import russell.john.client.view.MainView;
import russell.john.client.view.SideMenuView;
import russell.john.client.view.SettingsView;
import russell.john.client.view.LogView;

// Binds all the presenters, constants, widgets, servlets, etc.
public class ClientModule extends AbstractPresenterModule
{

	@Override
	protected void configure()
	{
			
		install(new DefaultModule(ClientPlaceManager.class));
		
		// Bind the default place to the sign in page
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.main);

		bindPresenter(MainPresenter.class, MainPresenter.MyView.class, MainView.class, MainPresenter.MyProxy.class);

		bindPresenterWidget(SideMenuPresenter.class, SideMenuPresenter.MyView.class, SideMenuView.class);

		bindPresenter(SettingsPresenter.class, SettingsPresenter.MyView.class, SettingsView.class, SettingsPresenter.MyProxy.class);

		bindPresenter(LogPresenter.class, LogPresenter.MyView.class, LogView.class, LogPresenter.MyProxy.class);
	}
}
