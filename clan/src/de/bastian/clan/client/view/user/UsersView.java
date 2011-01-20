package de.bastian.clan.client.view.user;

import java.util.ArrayList;
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
    CellTable<UserProxy> adminTable;

    @UiField
    CellTable<UserProxy> memberTable;

    @UiField
    CellTable<UserProxy> userTable;

    private void initTable() {
        /* Creating columns */
        Column<UserProxy, String> firstnameColumn = new Column<UserProxy, String>(new HtmlCell()) {
            @Override
            public String getValue(UserProxy user) {
                return "<a href='#user:" + user.getId() + "'>" + user.getFirstname() + "</a>";
            }
        };

        TextColumn<UserProxy> lastnameColumn = new TextColumn<UserProxy>() {
            @Override
            public String getValue(UserProxy user) {
                return user.getLastname();
            }
        };

        Column<UserProxy, Date> lastlogindateColumn = new Column<UserProxy, Date>(new DateCell(Clan.DATERENDERER)) {
            @Override
            public Date getValue(UserProxy user) {
                return user.getLastLogin();
            }
        };

        /* Init the admin table */
        adminTable.addColumn(firstnameColumn, Clan.MESSAGES.firstname());
        adminTable.addColumn(lastnameColumn, Clan.MESSAGES.lastname());
        adminTable.addColumn(lastlogindateColumn, Clan.MESSAGES.lastlogin());
        adminTable.addStyleName("grid");
        adminTable.setSelectionModel(new NoSelectionModel<UserProxy>());

        /* Init the member table */
        memberTable.addColumn(firstnameColumn, Clan.MESSAGES.firstname());
        memberTable.addColumn(lastnameColumn, Clan.MESSAGES.lastname());
        memberTable.addColumn(lastlogindateColumn, Clan.MESSAGES.lastlogin());
        memberTable.addStyleName("grid");
        memberTable.setSelectionModel(new NoSelectionModel<UserProxy>());

        /* Init the user table */
        userTable.addColumn(firstnameColumn, Clan.MESSAGES.firstname());
        userTable.addColumn(lastnameColumn, Clan.MESSAGES.lastname());
        userTable.addColumn(lastlogindateColumn, Clan.MESSAGES.lastlogin());
        userTable.addStyleName("grid");
        userTable.setSelectionModel(new NoSelectionModel<UserProxy>());
    }

    public void setUsers(List<UserProxy> users) {
        List<UserProxy> adminUsers = new ArrayList<UserProxy>();
        List<UserProxy> memberUsers = new ArrayList<UserProxy>();
        List<UserProxy> userUsers = new ArrayList<UserProxy>();

        for (UserProxy user : users) {
            switch (user.getType()) {
                case Admin:
                    adminUsers.add(user);
                    break;
                case Member:
                    memberUsers.add(user);
                    break;
                case User:
                    userUsers.add(user);
                    break;
            }
        }

        adminTable.setRowCount(adminUsers.size(), true);
        adminTable.setRowData(0, adminUsers);

        memberTable.setRowCount(memberUsers.size(), true);
        memberTable.setRowData(0, memberUsers);

        userTable.setRowCount(userUsers.size(), true);
        userTable.setRowData(0, userUsers);
    }

}
