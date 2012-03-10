package russell.john.client.view;

import java.util.Comparator;
import java.util.List;

import russell.john.client.presenter.LogPresenter;
import russell.john.shared.action.GetLogResult;
import russell.john.shared.action.GetLogType;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.ListDataProvider;

/**
 * Lets the user know how many times the application has trolled their friends.
 * 
 * @author John
 * 
 */
public class LogView extends ViewImpl implements LogPresenter.MyView
{

	private final Widget widget;
	@UiField(provided = true)
	CellTable<GetLogType> cellTable = new CellTable<GetLogType>();

	ListDataProvider<GetLogType> dataProvider = new ListDataProvider<GetLogType>();
	// Local data
	List<GetLogType> dataList;

	public interface Binder extends UiBinder<Widget, LogView>
	{
	}

	@Inject
	public LogView(final Binder binder)
	{
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget()
	{
		return widget;
	}

	@Override
	public void setLogInfo(GetLogResult result)
	{
		// We set the table
		setTable(result);
	}

	/**
	 * The table that holds the basic friends list.
	 * 
	 * @param result
	 */
	private void setTable(GetLogResult result)
	{
		if (cellTable.getColumnCount() == 4)
		{
			dataList.clear();
			dataList = dataProvider.getList();
			for (GetLogType friend : result.getResults())
				dataList.add(friend);
		}

		else
		{
			TextColumn<GetLogType> victimColumn = new TextColumn<GetLogType>()
			{
				@Override
				public String getValue(GetLogType log)
				{
					if (log.getFbVictimName() != null)
						return log.getFbVictimName();
					else
						return "";
				}
			};
			victimColumn.setSortable(true);

			TextColumn<GetLogType> commentPermalink = new TextColumn<GetLogType>()
			{
				@Override
				public String getValue(GetLogType log)
				{
					if (log.getFbCommentPermalink() != null)
						return log.getFbCommentPermalink();
					else
						return "";
				}
			};
			commentPermalink.setSortable(true);

			TextColumn<GetLogType> comment = new TextColumn<GetLogType>()
			{
				@Override
				public String getValue(GetLogType log)
				{
					if (log.getFbComment() != null)
						return log.getFbComment();
					else
						return "";
				}
			};
			comment.setSortable(true);

			TextColumn<GetLogType> date = new TextColumn<GetLogType>()
			{
				@Override
				public String getValue(GetLogType log)
				{
					if (log.getDate() != null)
						return log.getDate().toString();
					else
						return "";
				}
			};
			date.setSortable(true);

			cellTable.addColumn(victimColumn, "Name");
			cellTable.addColumn(commentPermalink, "Comment Link");
			cellTable.addColumn(comment, "Comment Text");
			cellTable.addColumn(date, "Time");

			// Attach provider
			dataProvider.addDataDisplay(cellTable);
			dataList = dataProvider.getList();
			for (GetLogType friend : result.getResults())
				dataList.add(friend);

			ListHandler<GetLogType> columnSortHandler = new ListHandler<GetLogType>(dataList);
			columnSortHandler.setComparator(victimColumn, new Comparator<GetLogType>()
			{

				@Override
				public int compare(GetLogType o1, GetLogType o2)
				{
					if (o1 == o2)
					{
						return 0;
					}

					// Compare the name columns.
					if (o1 != null)
					{
						return (o2 != null) ? o1.getFbVictimName().compareTo(o2.getFbVictimName()) : 1;
					}
					return -1;
				}
			});

			columnSortHandler.setComparator(commentPermalink, new Comparator<GetLogType>()
			{

				@Override
				public int compare(GetLogType o1, GetLogType o2)
				{
					if (o1 == o2)
					{
						return 0;
					}

					// Compare the role columns.
					if (o1 != null)
					{
						return (o2 != null) ? o1.getFbCommentPermalink().compareTo(o2.getFbCommentPermalink()) : 1;
					}
					return -1;
				}
			});

			columnSortHandler.setComparator(comment, new Comparator<GetLogType>()
			{
				@Override
				public int compare(GetLogType o1, GetLogType o2)
				{
					if (o1 == o2)
					{
						return 0;
					}

					// Compare the role columns.
					if (o1 != null)
					{
						return (o2 != null) ? o1.getFbComment().compareTo(o2.getFbComment()) : 1;
					}
					return -1;
				}
			});

			columnSortHandler.setComparator(date, new Comparator<GetLogType>()
			{

				@Override
				public int compare(GetLogType o1, GetLogType o2)
				{
					if (o1 == o2)
					{
						return 0;
					}

					// Compare the role columns.
					if (o1 != null)
					{
						return (o2 != null) ? o1.getDate().compareTo(o2.getDate()) : 1;
					}
					return -1;
				}
			});

			cellTable.addColumnSortHandler(columnSortHandler);
			cellTable.getColumnSortList().push(date);
			cellTable.setPageSize(5000);
		}
	}
}
