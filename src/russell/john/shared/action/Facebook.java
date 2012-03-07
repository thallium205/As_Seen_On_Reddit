package russell.john.shared.action;

import java.util.ArrayList;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.ActionImpl;


/**
 * The @GenDispatch will generate Action and Result classes for the FacebookAction.
 */
@GenDispatch(isSecure = false, serviceName = ActionImpl.DEFAULT_SERVICE_NAME)
public class Facebook
{
	@In(1)
	String authCode;	
	@Out(1)
	String authToken;
	@Out(2)
	String fbId;
	@Out(3)
	String fbName;
	@Out(4)
	ArrayList<FacebookFriendType> fbFriends;
}
