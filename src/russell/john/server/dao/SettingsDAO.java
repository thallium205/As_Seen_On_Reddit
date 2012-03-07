package russell.john.server.dao;

import russell.john.server.domain.UserSettings;

import com.googlecode.objectify.ObjectifyService;

/**
 * The settings Objectify DAO.  Needs to call ObjectifyService.register to work properly.
 * @author John
 *
 */
public class SettingsDAO extends ObjectifyDAO<Object>
{
	static
	{
		ObjectifyService.register(UserSettings.class);
	}
}
