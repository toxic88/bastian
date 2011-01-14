package de.bastian.clan.client.view.user;

import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.view.widgets.HtmlCell;
import de.bastian.clan.shared.UserProxy;

public class UsersView extends Composite {

    public interface UsersViewConstants extends Constants {
        String firstname();
        String lastname();
        String lastlogin();
    }

    private static UsersViewUiBinder uiBinder = GWT.create(UsersViewUiBinder.class);

    interface UsersViewUiBinder extends UiBinder<Widget, UsersView> {}

    public UsersView() {
        initWidget(uiBinder.createAndBindUi(this));

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                initTable();
            }
        });
    }

    @UiField
    CellTable<UserProxy> table;

    private void initTable() {
        Column<UserProxy, String> firstnameColumn = new Column<UserProxy, String>(new HtmlCell()) {
            @Override
            public String getValue(UserProxy user) {
                return "<a href='#user:" + user.getId() + "'>" + user.getFirstname() + "</a>";
            }
        };
        table.addColumn(firstnameColumn, Clan.MESSAGES.firstname());

        TextColumn<UserProxy> lastnameColumn = new TextColumn<UserProxy>() {
            @Override
            public String getValue(UserProxy user) {
                return user.getLastname();
            }
        };
        table.addColumn(lastnameColumn, Clan.MESSAGES.lastname());

        Column<UserProxy, Date> lastlogindateColumn = new Column<UserProxy, Date>(new DateCell(Clan.DATERENDERER)) {
            @Override
            public Date getValue(UserProxy user) {
                return user.getLastLogin();
            }
        };
        table.addColumn(lastlogindateColumn, Clan.MESSAGES.lastlogin());

        table.addStyleName("grid");
        table.setSelectionModel(new NoSelectionModel<UserProxy>());
    }

    public void setUsers(List<UserProxy> users) {
        table.setRowCount(users.size(), true);
        table.setRowData(0, users);
    }

}
