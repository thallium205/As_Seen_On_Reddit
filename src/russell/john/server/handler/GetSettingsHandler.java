package russell.john.server.handler;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import russell.john.server.dao.SettingsDAO;
import russell.john.server.domain.UserSettings;
import russell.john.shared.action.GetSettingsAction;
import russell.john.shared.action.GetSettingsResult;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * When a user lands on the page after validation, this action is called to try
 * and get the user's information. If the user does not exist yet, the
 * application will create one.
 * 
 * @author John
 * 
 */
public class GetSettingsHandler implements ActionHandler<GetSettingsAction, GetSettingsResult>
{
	// private Provider<HttpServletRequest> requestProvider;

	@Inject
	GetSettingsHandler(final ServletContext servletContext, final Provider<HttpServletRequest> requestProvider)
	{
		// this.requestProvider = requestProvider;
	}

	@Override
	public GetSettingsResult execute(final GetSettingsAction action, final ExecutionContext context) throws ActionException
	{

		SettingsDAO dao = new SettingsDAO();
		UserSettings settings = dao.ofy().query(UserSettings.class).filter("fbId", action.getFbId()).get();

		// Create a new user if one does not exist
		if (settings == null)
		{
			settings = new UserSettings(action.getFbId(), 50, "As seen on Reddit -> ", new Date(0L), new ArrayList<String>(), action.getAuthToken());
			dao.put(settings);
		}

		// Update an existing user to get a fresher auth token
		else
		{
			settings.setAuthToken(action.getAuthToken());
			dao.put(settings);
		}

		return new GetSettingsResult(settings.getComment(), settings.getRedditThreshold(), settings.getLastCheckedDate(), settings.getFriends());
	}

	@Override
	public Class<GetSettingsAction> getActionType()
	{
		return GetSettingsAction.class;
	}

	@Override
	public void undo(GetSettingsAction action, GetSettingsResult result, ExecutionContext context) throws ActionException
	{
	}
}
