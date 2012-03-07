package russell.john.server.dao;

import russell.john.server.domain.UserLog;

import com.googlecode.objectify.ObjectifyService;

/**
 * The Log Objectify DAO.  Needs to call ObjectifyService.register to work properly.
 * @author John
 *
 */
public class LogDAO extends ObjectifyDAO<Object>
{
	static
	{
		ObjectifyService.register(UserLog.class);
	}
}
