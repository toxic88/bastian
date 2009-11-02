package de.bastian.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.GWT;

public class Application {

    public static enum Services {
        USER
    };

    public void onModuleLoad() {
        UserManagerAsync service = (UserManagerAsync) GWT.create(UserManager.class);
        Registry.register(Services.USER.name(), service);

        Dispatcher dispatcher = Dispatcher.get();
        //dispatcher.addController(new AppController());

        dispatcher.dispatch(AppEvents.Login);

        //GXT.hideLoadingPanel("loading");
    }

}
