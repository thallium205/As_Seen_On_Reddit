package russell.john.server.guice;

import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.Injector;
import com.google.inject.Guice;
import russell.john.server.guice.ServerModule;
import russell.john.server.guice.DispatchServletModule;

/**
 * 
 * @author John
 *
 */
public class GuiceServletConfig extends GuiceServletContextListener
{

	@Override
	protected Injector getInjector()
	{
		return Guice.createInjector(new ServerModule(), new DispatchServletModule());
	}
}
