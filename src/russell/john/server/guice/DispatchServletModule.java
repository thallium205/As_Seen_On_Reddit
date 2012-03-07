package russell.john.server.guice;

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.shared.ActionImpl;
import com.gwtplatform.dispatch.server.guice.DispatchServiceImpl;

/**
 * This Guice listener hijacks all further filters and servlets. Extra filters
 * and servlets have to be configured in your ServletModule#configureServlets()
 * by calling serve(String).with(Class<? extends HttpServlet>) and
 * filter(String).through(Class<? extends Filter)(non-Javadoc)
 * 
 * 
 * @author John
 * 
 */
public class DispatchServletModule extends ServletModule
{

	@Override
	public void configureServlets()
	{
		serve("/" + ActionImpl.DEFAULT_SERVICE_NAME).with(DispatchServiceImpl.class);
	}
}
