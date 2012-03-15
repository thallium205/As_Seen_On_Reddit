package russell.john.shared.action;

import java.util.ArrayList;
import java.util.Date;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.ActionImpl;

/**
 * The input/output class for the GetSettingsAction class.
 * @author John
 *
 */
@GenDispatch(isSecure = false, serviceName = ActionImpl.DEFAULT_SERVICE_NAME)
public class GetSettings
{
	@In(1)
	String fbId;
	
	@In(2)
	String authToken;
	
	@Out(1)
	String comment;
	
	@Out(2)
	Integer redditThreshold;
	
	@Out(3)
	Date lastCheckedDate;
	
	@Out(4)
	ArrayList<String> friends;
}
