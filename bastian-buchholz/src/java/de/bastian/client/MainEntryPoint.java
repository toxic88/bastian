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
import de.bastian.client.rpc.ServiceManager;
import java.util.List;

public class MainEntryPoint implements EntryPoint {

    final FormPanel fp = new FormPanel();
    final FlexTable table = new FlexTable();
    final TextField<String> username = new TextField<String>();
    final TextField<String> password = new TextField<String>();

    public void reloadTable() {
        AsyncCallback<List<String[]>> callback = new AsyncCallback<List<String[]>>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.toString());
            }

            public void onSuccess(List<String[]> result) {
                for (int i = 0;i < table.getRowCount();i++) {
                    table.removeRow(i);
                }
                int i = 0;
                for (String[] user : result) {
                    int j = 0;
                    for (String field : user) {
                        table.setText(i, j, field);
                        j++;
                    }
                    i++;
                }
            }
        };

        ServiceManager.getUserService().getAll(callback);
    }

    public void onModuleLoad() {

        Button createUserBtn = new Button("Create new User!", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {

                if (username.getValue() == null || password.getValue() == null) {
                    return;
                }

                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.toString());
                    }

                    public void onSuccess(Boolean result) {
                        if (!result) {
                            Window.alert("User allready exists!");
                        } else {
                            reloadTable();
                        }
                    }
                };

                ServiceManager.getUserService().createUser(username.getValue(), password.getValue(), callback);

                username.setValue(null);
                password.setValue(null);
            }
        });

        Button listUserBtn = new Button("Get all users!", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {
                reloadTable();
            }
        });

        Button loginButton = new Button("Login", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                LoginWindow.get().show();
            }
        });

        username.setFieldLabel("First Name");
        password.setFieldLabel("Last Name");

        fp.setHeading("Create new user");
        fp.setWidth(300);
        fp.setFrame(true);
        fp.add(username, new FormData("100%"));
        fp.add(password, new FormData("100%"));
        fp.addButton(createUserBtn);
        fp.addButton(listUserBtn);
        fp.addButton(loginButton);

        RootPanel.get().add(fp);
        RootPanel.get().add(table);

    }

}
