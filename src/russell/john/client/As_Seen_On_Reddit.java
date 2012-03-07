package russell.john.client;

import com.google.gwt.core.client.EntryPoint;
import russell.john.client.gin.ClientGinjector;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Application hook that starts the party.
 * @author John
 *
 */
public class As_Seen_On_Reddit implements EntryPoint
{

	private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);

	@Override
	public void onModuleLoad()
	{
		// This is required for Gwt-Platform proxy's generator
		DelayedBindRegistry.bind(ginjector);
	
		ginjector.getPlaceManager().revealCurrentPlace();
	}
}
