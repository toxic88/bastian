package de.bastian.client.controller;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.MessageBox;

import de.bastian.client.Application;
import de.bastian.client.AppEvents;
import de.bastian.client.view.AppView;

public class AppController extends Controller {

  private AppView appView;

  public AppController() {
    this.registerEventTypes(AppEvents.Init);
    this.registerEventTypes(AppEvents.Error);
  }

  @Override
  protected void initialize() {
    this.appView = new AppView(this);
  }

  @Override
  public void handleEvent(AppEvent event) {
    if (event.getType() == AppEvents.Init) {
      this.forwardToView(this.appView, event);
    } else if (event.getType() == AppEvents.Error) {
      MessageBox.alert(Application.MESSAGES.error(), event.<String>getData(), null);
    }
  }

}
