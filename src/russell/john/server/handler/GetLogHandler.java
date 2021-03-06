package russell.john.server.handler;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import russell.john.server.dao.LogDAO;
import russell.john.server.domain.UserLog;
import russell.john.shared.action.GetLogAction;
import russell.john.shared.action.GetLogResult;
import russell.john.shared.action.GetLogType;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
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
public class GetLogHandler implements ActionHandler<GetLogAction, GetLogResult>
{
	// private Provider<HttpServletRequest> requestProvider;

	@Inject
	GetLogHandler(final ServletContext servletContext, final Provider<HttpServletRequest> requestProvider)
	{
		// this.requestProvider = requestProvider;
	}

	@Override
	public GetLogResult execute(final GetLogAction action, final ExecutionContext context) throws ActionException
	{
		ArrayList<GetLogType> logs = new ArrayList<GetLogType>();

		// Get all the user's logs
		LogDAO dao = new LogDAO();
		QueryResultIterable<UserLog> userLog = dao.ofy().query(UserLog.class).filter("fbId", action.getFbId()).fetch();
		
		// Resolve the vbVictimIds to the names and create a sendable log item as inneficiently as possible since I'm lazy
		for (QueryResultIterator<UserLog> userIter = userLog.iterator(); userIter.hasNext();)
		{
			UserLog logItem = userIter.next();
			logs.add(new GetLogType(logItem.getFbVictimId(), logItem.getFbCommentPermalink(), logItem.getFbComment(), logItem.getDate()));
		}

		return new GetLogResult(logs);
	}

	@Override
	public Class<GetLogAction> getActionType()
	{
		return GetLogAction.class;
	}

	@Override
	public void undo(GetLogAction action, GetLogResult result, ExecutionContext context) throws ActionException
	{
	}
}
