package de.bastian.client;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.bastian.client.rpc.ServiceManager;
import de.bastian.client.overrides.FormPanel;

public class LoginWindow extends Window {

    private static LoginWindow instance = null;

    private FormPanel fp;

    private TextField<String> username;
    private TextField<String> password;

    private Button loginBtn;
    private Button cancelBtn;

    private FormData formData = new FormData("100%");

    public LoginWindow() {
        this.setHeading("Login");
        
        this.fp = new FormPanel();
        this.fp.setHeaderVisible(false);
        this.fp.setBorders(false);
        this.fp.setBodyBorder(false);
        this.fp.setPadding(5);
        this.fp.setLabelWidth(60);

        this.username = new TextField<String>();
        this.username.setFieldLabel("Username");
        this.username.setAllowBlank(false);
        this.username.setMessageTarget("tooltip");

        this.password = new TextField<String>();
        this.password.setFieldLabel("Password");
        this.password.setAllowBlank(false);
        this.password.setMessageTarget("tooltip");
        this.password.setPassword(true);
        

        this.loginBtn = new Button("Login", new SelectionListener<ButtonEvent>() {
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
        
        this.cancelBtn = new Button("Cancel", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                LoginWindow.get().hide();
            }
        });

        this.setLayout(new FitLayout());

        this.fp.add(this.username, this.formData);
        this.fp.add(this.password, this.formData);

        this.addButton(this.loginBtn);
        this.addButton(this.cancelBtn);

        this.add(this.fp);

        this.setModal(true);
        this.setFocusWidget(this.username);
        this.setDraggable(false);
        this.setResizable(false);
        this.setWidth(250);
        this.setHeight(126);

        new FormButtonBinding(this.fp).addButton(this.loginBtn);
    }

    public static LoginWindow get() {
        if (LoginWindow.instance == null) {
            LoginWindow.instance = new LoginWindow();
        }

        return LoginWindow.instance;
    }

    @Override
    protected void onShow() {
        super.onShow();
        this.username.setValue(null);
        this.password.setValue(null);
        this.fp.clearInvalid();
    }
    
}
