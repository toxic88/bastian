package de.bastian.client;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.bastian.client.rpc.ServiceManager;
import de.bastian.client.overrides.FormPanel;

public class LoginWindow {

    private static Window win = null;

    private static FormData formData = new FormData("100%");

    public static Window get() {
        if (LoginWindow.win == null) {
            final FormPanel fp = new FormPanel();
            fp.setHeaderVisible(false);
            fp.setBorders(false);
            fp.setBodyBorder(false);
            fp.setPadding(5);
            fp.setLabelWidth(60);

            final TextField<String> username = new TextField<String>();
            username.setFieldLabel("Username");
            username.setAllowBlank(false);
            username.setMessageTarget("tooltip");

            final TextField<String> password = new TextField<String>();
            password.setFieldLabel("Password");
            password.setAllowBlank(false);
            password.setMessageTarget("tooltip");
            password.setPassword(true);

            fp.add(username, formData);
            fp.add(password, formData);

            Button loginBtn = new Button("Login", new SelectionListener<ButtonEvent>() {
                @Override
                public void componentSelected(ButtonEvent ce) {

                    if (username.getValue() == null || password.getValue() == null) {
                        return;
                    }

                    AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                        public void onFailure(Throwable caught) {
                            com.google.gwt.user.client.Window.alert(caught.toString());
                        }

                        public void onSuccess(Boolean result) {
                            com.google.gwt.user.client.Window.alert(result.toString());
                            LoginWindow.get().hide();
                        }
                    };

                    ServiceManager.getUserService().login(username.getValue(), password.getValue(), callback);

                    username.setValue(null);
                    password.setValue(null);
                }
            });

            new FormButtonBinding(fp).addButton(loginBtn);

            Button cancelBtn = new Button("Cancel", new SelectionListener<ButtonEvent>() {
                @Override
                public void componentSelected(ButtonEvent ce) {
                    LoginWindow.get().hide();
                }
            });

            win = new Window();

            win.setLayout(new FitLayout());

            win.addButton(loginBtn);
            win.addButton(cancelBtn);

            win.add(fp);
            
            win.setHeading("Login");
            win.setModal(true);
            win.setFocusWidget(username);
            win.setDraggable(false);
            win.setResizable(false);
            win.setWidth(250);
            win.setHeight(126);

            win.addListener(Events.BeforeShow, new Listener<WindowEvent>() {
                @Override
                public void handleEvent(WindowEvent be) {
                    username.setValue(null);
                    password.setValue(null);
                    fp.clearInvalid();
                }
            });

            LoginWindow.win = win;
        }

        return LoginWindow.win;
    }

}
