package russell.john.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import russell.john.server.dao.SettingsDAO;
import russell.john.server.domain.UserSettings;
import russell.john.shared.action.SetSettingsAction;
import russell.john.shared.action.SetSettingsResult;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * 
 * The main action to perform. This handler will save a user's settings and
 * commit it to the datastore. A user will always exist because
 * GetSettingsResult will generate a user if one does not exist.
 * 
 * Then, it will scan a user's feed and check their friend's links against
 * reddit. If there is a result, it will post on the link that it has been on
 * reddit already.
 * 
 * 1) Get all the users out of the datastore. Use an iterable to prevent high
 * memory consumption 
 * 2) Get the user's feed and pull out all posted link while
 * timestamp has not been surpassed from the record in the database and will
 * skip the user himself if he posts a link (ie: look through everything until
 * we've already checked all previous records) 
 * 3)For each link, search against reddit and sort by what the user wanted.
 * 4) Post in the comment section of the comment
 * 5) Done
 * 
 * @author John
 * 
 */
public class SetSettingsHandler implements ActionHandler<SetSettingsAction, SetSettingsResult>
{
	// private Provider<HttpServletRequest> requestProvider;

	/**
	 * Injection to get the request provider. Needed in certain situations.
	 * 
	 * @param servletContext
	 * @param requestProvider
	 */
	@Inject
	SetSettingsHandler(final ServletContext servletContext, final Provider<HttpServletRequest> requestProvider)
	{
		// this.requestProvider = requestProvider;
	}

	@Override
	public SetSettingsResult execute(final SetSettingsAction action, final ExecutionContext context) throws ActionException
	{
		setUserSettings(action);
		
		beginTrolling();

		// We don't care because we don't return anything after a user sets
		// their settings
		return new SetSettingsResult();
	}

	/**
	 * The class type.
	 */
	@Override
	public Class<SetSettingsAction> getActionType()
	{
		return SetSettingsAction.class;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.gwtplatform.dispatch.shared.Action,
	 *      com.gwtplatform.dispatch.shared.Result,
	 *      com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(SetSettingsAction action, SetSettingsResult result, ExecutionContext context) throws ActionException
	{
	}
	
	/**
	 * Saves the user's settings before begining main troll operation
	 * @param action
	 */
	private void setUserSettings(final SetSettingsAction action)
	{
		SettingsDAO dao = new SettingsDAO();
		UserSettings settings = dao.ofy().query(UserSettings.class).filter("fbId", action.getFbId()).get();

		settings.setComment(action.getComment());
		settings.setFriends(action.getFriends());

		dao.put(settings);
	}
	
	/**
	 * The main troll operation.
	 */
	private void beginTrolling()
	{
		
	}
	
	
}
