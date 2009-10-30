package de.bastian.client;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import java.util.List;

public class MainEntryPoint implements EntryPoint {

    final FormPanel fp = new FormPanel();
    final FlexTable table = new FlexTable();
    final TextField<String> firstName = new TextField<String>();
    final TextField<String> lastName = new TextField<String>();

    public void reloadTable() {
        AsyncCallback<List<String[]>> callback = new AsyncCallback<List<String[]>>() {
            public void onFailure(Throwable caught) {
                // TODO: Do something with errors.
            }

            public void onSuccess(List<String[]> result) {
                for (int i = 0;i < table.getRowCount();i++) {
                    table.removeRow(i);
                }
                int i = 0;
                for (String[] user : result) {
                    table.setText(i, 0, user[0]);
                    table.setText(i, 1, user[1]);
                    table.setText(i, 2, user[2]);
                    table.setText(i, 3, user[3]);
                    i++;
                }
            }
        };

        getService().getList(callback);
    }

    public void onModuleLoad() {

        Button createUserBtn = new Button("Create new User!", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {

                if (firstName.getValue() == null || lastName.getValue() == null) {
                    return;
                }

                AsyncCallback<Void> callback = new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        // TODO: Do something with errors.
                    }

                    public void onSuccess(Void result) {
                        reloadTable();
                    }
                };

                getService().createUser(firstName.getValue(), lastName.getValue(), "test", callback);

                firstName.setValue(null);
                lastName.setValue(null);
            }
        });

        Button listUserBtn = new Button("Get all users!", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {
                reloadTable();
            }
        });

        Button checkPasswordBtn = new Button("Check password!", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        // TODO: Do something with errors.
                    }

                    public void onSuccess(Boolean result) {
                        Window.alert(result.toString());
                    }
                };

                getService().checkPassword("tes", callback);
            }
        });

        firstName.setFieldLabel("First Name");
        lastName.setFieldLabel("Last Name");

        fp.setHeading("Create new user");
        fp.setWidth(300);
        fp.setFrame(true);
        fp.add(firstName, new FormData("100%"));
        fp.add(lastName, new FormData("100%"));
        fp.addButton(createUserBtn);
        fp.addButton(listUserBtn);
        fp.addButton(checkPasswordBtn);

        RootPanel.get().add(fp);
        RootPanel.get().add(table);

    }

    public static UserManagerAsync getService() {
        return GWT.create(UserManager.class);
    }

}
