package russell.john.shared.action;

import java.util.ArrayList;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.GenDto;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.ActionImpl;

/**
 * The input/output class for a log
 * @author John
 *
 */
@GenDto
@GenDispatch(isSecure = false, serviceName = ActionImpl.DEFAULT_SERVICE_NAME)
public class GetLog
{
	@In(1)
	String fbId;
	
	@Out(1)
	ArrayList<GetLogType> results;
}
