package russell.john.client.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import russell.john.client.presenter.SettingsPresenter;
import russell.john.shared.action.FacebookFriendType;
import russell.john.shared.action.FacebookResult;
import russell.john.shared.action.GetSettingsResult;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.IntegerBox;

/**
 * The main page the appliction. Its where they specify their settings and
 * perform the action.
 * 
 * @author John
 * 
 */
public class SettingsView extends ViewWithUiHandlers<SettingsUiHandlers> implements SettingsPresenter.MyView
{
	@UiField
	TextBox boxText;
	@UiField
	CheckBox chkEveryone;
	@UiField
	Button btnApply;
	@UiField(provided = true)
	CellTable<FacebookFriendType> cellTable = new CellTable<FacebookFriendType>();
	@UiField TextArea lblApplyConfirm;
	@UiField IntegerBox boxRedditThreshold;
	private final Widget widget;

	ListDataProvider<FacebookFriendType> dataProvider = new ListDataProvider<FacebookFriendType>();

	// Local data
	List<FacebookFriendType> dataList;

	public interface Binder extends UiBinder<Widget, SettingsView>
	{

	}

	@Inject
	public SettingsView(final Binder binder)
	{
		widget = binder.createAndBindUi(this);
		setUiHandlers();
	}

	private void setUiHandlers()
	{
		chkEveryone.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				boolean checked = ((CheckBox) event.getSource()).getValue();

				if (checked)
				{
					cellTable.setVisible(false);
				}

				else
				{
					cellTable.setVisible(true);
				}
			}

		});

	}

	@UiHandler("btnApply")
	void onBtnTribesClick(ClickEvent event)
	{
		if (getUiHandlers() != null)
		{
			lblApplyConfirm.setText("Working... please wait.");
			btnApply.setEnabled(false);
			getUiHandlers().onBtnApply();
		}
	}

	@Override
	public Widget asWidget()
	{
		return widget;
	}

	@Override
	public void setFacebookInfo(FacebookResult result)
	{
		// We set the table
		setTable(result);
	}

	/**
	 * The table that holds the basic friends list.
	 * 
	 * @param result
	 */
	private void setTable(FacebookResult result)
	{
		final MultiSelectionModel<FacebookFriendType> selectionModel = new MultiSelectionModel<FacebookFriendType>(FacebookFriendType.KEY_PROVIDER);
		cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<FacebookFriendType> createCheckboxManager());

		Column<FacebookFriendType, Boolean> checkColumn = new Column<FacebookFriendType, Boolean>(new CheckboxCell(true, false))
		{
			@Override
			public Boolean getValue(FacebookFriendType object)
			{
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};

		TextColumn<FacebookFriendType> idColumn = new TextColumn<FacebookFriendType>()
		{
			@Override
			public String getValue(FacebookFriendType friend)
			{
				if (friend.getId() != null)
					return friend.getId();
				else
					return "";
			}
		};
		idColumn.setSortable(true);

		TextColumn<FacebookFriendType> nameColumn = new TextColumn<FacebookFriendType>()
		{
			@Override
			public String getValue(FacebookFriendType friend)
			{
				if (friend.getId() != null)
					return friend.getName();
				else
					return "";
			}
		};
		nameColumn.setSortable(true);

		cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		cellTable.addColumn(idColumn, "ID");
		cellTable.addColumn(nameColumn, "Name");
		
		// Fix sizes
		cellTable.setWidth("675px", true);
		cellTable.setColumnWidth(checkColumn, 10, Unit.PCT);
		cellTable.setColumnWidth(idColumn, 20, Unit.PCT);
		cellTable.setColumnWidth(nameColumn, 70, Unit.PCT);	

		// Attach provider
		dataProvider.addDataDisplay(cellTable);
		dataList = dataProvider.getList();
		for (FacebookFriendType friend : result.getFbFriends())
			dataList.add(friend);
		
		// Set row count
		cellTable.setRowCount(dataList.size());
		cellTable.setPageSize(20);

		ListHandler<FacebookFriendType> columnSortHandler = new ListHandler<FacebookFriendType>(dataList);
		columnSortHandler.setComparator(idColumn, new Comparator<FacebookFriendType>()
		{

			@Override
			public int compare(FacebookFriendType o1, FacebookFriendType o2)
			{
				if (o1 == o2)
				{
					return 0;
				}

				// Compare the name columns.
				if (o1 != null)
				{
					return (o2 != null) ? o1.getId().compareTo(o2.getId()) : 1;
				}
				return -1;
			}
		});

		columnSortHandler.setComparator(nameColumn, new Comparator<FacebookFriendType>()
		{

			@Override
			public int compare(FacebookFriendType o1, FacebookFriendType o2)
			{
				if (o1 == o2)
				{
					return 0;
				}

				// Compare the role columns.
				if (o1 != null)
				{
					return (o2 != null) ? o1.getName().compareTo(o2.getName()) : 1;
				}
				return -1;
			}
		});

		cellTable.addColumnSortHandler(columnSortHandler);
		cellTable.getColumnSortList().push(nameColumn);
		cellTable.setPageSize(5000);
	}

	@Override
	public void setSettingsInfo(GetSettingsResult result)
	{
		// Display the time
		lblApplyConfirm.setText("Last used: " + result.getLastCheckedDate().toString() + ".  Any posts made by your friends before this date will be skipped to prevent duplicates.");
		
		// Display the reddit threshold settings
		boxRedditThreshold.setValue(result.getRedditThreshold());	
		
		// If there are custom victims defined, then we show the table. Else, we
		// hide the table
		if (result.getFriends() == null || result.getFriends().isEmpty())
		{
			chkEveryone.setValue(true);
			cellTable.setVisible(false);
		}

		// This means they have a custom victim list.
		else
		{
			chkEveryone.setValue(false);
			cellTable.setVisible(true);

			boxText.setText(result.getComment());
			// Due to facebook policy I am only allowed to store people's user
			// ids.
			// as a result, i need to check the dataprovider against the string
			// values, then when there is a match, enable them.
			for (Iterator<FacebookFriendType> iter = dataProvider.getList().iterator(); iter.hasNext();)
			{
				FacebookFriendType friend = iter.next();
				for (Iterator<String> iter2 = result.getFriends().iterator(); iter2.hasNext();)
				{
					String friendId = iter2.next();

					if (friendId.contains(friend.getId()))
					{
						cellTable.getSelectionModel().setSelected(friend, true);
					}
				}
			}
		}
	}

	@Override
	public String getUserText()
	{
		return boxText.getText();
	}

	@Override
	public ArrayList<String> getSelectedUsers()
	{
		ArrayList<String> selectedUsers = new ArrayList<String>();
		if (chkEveryone.getValue())
			return selectedUsers;

		else
		{
			for (Iterator<FacebookFriendType> iter = dataProvider.getList().iterator(); iter.hasNext();)
			{
				FacebookFriendType friend = iter.next();

				if (cellTable.getSelectionModel().isSelected(friend))
				{
					selectedUsers.add(friend.getId());
				}
			}
		}

		return selectedUsers;
	}

	@Override
	public void setApplyConfirm(String text)
	{
		btnApply.setEnabled(true);
		lblApplyConfirm.setText(text);
	}

	@Override
	public Integer getRedditThreshold()
	{
		return boxRedditThreshold.getValue();
	}
}
