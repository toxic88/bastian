package de.bastian.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import de.bastian.client.controller.LoginController;

public class Application implements EntryPoint {

    public static enum Services {
        USER
    };

    public void onModuleLoad() {
        UserManagerAsync service = (UserManagerAsync) GWT.create(UserManager.class);
        Registry.register(Application.Services.USER.name(), service);

        Dispatcher dispatcher = Dispatcher.get();
        dispatcher.addController(new LoginController());

        dispatcher.dispatch(AppEvents.Login);

        //GXT.hideLoadingPanel("loading");
    }

}
