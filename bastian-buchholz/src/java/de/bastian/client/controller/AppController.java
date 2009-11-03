package de.bastian.client.controller;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import de.bastian.client.AppEvents;
import de.bastian.client.view.AppView;

public class AppController extends Controller {

    private AppView appView;

    public AppController() {
        this.registerEventTypes(AppEvents.Init);
    }

    @Override
    protected void initialize() {
        this.appView = new AppView(this);
    }

    @Override
    public void handleEvent(AppEvent event) {
        if (event.getType() == AppEvents.Init) {
            this.forwardToView(this.appView, event);
        }
    }

}
