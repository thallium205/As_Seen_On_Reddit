package russell.john.shared.action;

import java.util.ArrayList;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.ActionImpl;

/**
 * The input/output class for the SetSettingsAction
 * @author John
 *
 */
@GenDto
@GenDispatch(isSecure = false, serviceName = ActionImpl.DEFAULT_SERVICE_NAME)
public class SetSettings
{
	
	@In(1)
	String fbId;
	
	@In(2)
	String comment;
	
	@In(3)
	ArrayList<String> friends;
	
	@Out(1)
	ArrayList<String> linksChecked;
	
	@Out(2)
	int linksMatched;
}
