package russell.john.server.guice;

import russell.john.server.handler.FacebookHandler;
import russell.john.server.handler.GetSettingsHandler;
import russell.john.server.handler.SetSettingsHandler;
import russell.john.shared.action.FacebookAction;
import russell.john.shared.action.GetSettingsAction;
import russell.john.shared.action.SetSettingsAction;

import com.gwtplatform.dispatch.server.guice.HandlerModule;

/**
 * Binds all the handlers and actions with Guice
 * @author John
 *
 */
public class ServerModule extends HandlerModule
{
	@Override
	protected void configureHandlers()
	{
		bindHandler(FacebookAction.class, FacebookHandler.class);
		bindHandler(GetSettingsAction.class, GetSettingsHandler.class);
		bindHandler(SetSettingsAction.class, SetSettingsHandler.class);
	}
}
