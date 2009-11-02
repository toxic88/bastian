package de.bastian.client.controller;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import de.bastian.client.AppEvents;
import de.bastian.client.view.LoginView;

public class LoginController extends Controller {

    private LoginView loginView;
    
    public LoginController() {
        registerEventTypes(AppEvents.Login);
    }

    @Override
    protected void initialize() {
        loginView = new LoginView(this);
    }

    @Override
    public void handleEvent(AppEvent event) {
        EventType type = event.getType();
        if (type == AppEvents.Login) {
            forwardToView(loginView, event);
        }
    }

}
