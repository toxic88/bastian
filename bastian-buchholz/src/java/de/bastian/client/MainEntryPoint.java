package de.bastian.client;

import de.bastian.client.widget.UserGrid;
import de.bastian.client.widget.LoginWindow;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import de.bastian.client.rpc.ServiceManager;
import de.bastian.client.model.User;
import de.bastian.client.overrides.EditorGrid;

public class MainEntryPoint extends Viewport implements EntryPoint {

    final FormPanel fp = new FormPanel();
    public EditorGrid<User> table = UserGrid.get();
    public TextField<String> username = new TextField<String>();
    public TextField<String> password = new TextField<String>();

    public MainEntryPoint() {
        this.setLayout(new BorderLayout());
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
                            table.getStore().getLoader().load();
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
                table.getStore().getLoader().load();
            }
        });

        Button loginButton = new Button("Login", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                LoginWindow.get().show();
            }
        });

        username.setFieldLabel("First Name");
        password.setFieldLabel("Password");
        password.setPassword(true);

        fp.setHeading("Create new user");
        fp.setWidth(300);
        fp.setFrame(true);
        fp.add(username, new FormData("100%"));
        fp.add(password, new FormData("100%"));
        fp.addButton(createUserBtn);
        fp.addButton(listUserBtn);
        fp.addButton(loginButton);

        this.add(fp, new BorderLayoutData(LayoutRegion.CENTER));
        this.add(table, new BorderLayoutData(LayoutRegion.SOUTH));
        
        RootPanel.get().add(this);
    }

}
