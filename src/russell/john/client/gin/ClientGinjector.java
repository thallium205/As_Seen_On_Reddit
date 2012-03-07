package russell.john.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import russell.john.client.gin.ClientModule;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.google.inject.Provider;

import russell.john.client.presenter.LogPresenter;
import russell.john.client.presenter.MainPresenter;
import russell.john.client.presenter.SettingsPresenter;
import russell.john.client.presenter.SideMenuPresenter;

import com.google.gwt.inject.client.AsyncProvider;
/**
 * Required for the code split to work.
 * @author John
 *
 */
@GinModules({ DispatchAsyncModule.class, ClientModule.class })
public interface ClientGinjector extends Ginjector
{

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	Provider<MainPresenter> getMainPresenter();

	AsyncProvider<SideMenuPresenter> getSideMenuPresenter();

	AsyncProvider<SettingsPresenter> getSettingsPresenter();

	AsyncProvider<LogPresenter> getLogPresenter();
}
