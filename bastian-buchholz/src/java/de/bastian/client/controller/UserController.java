package de.bastian.client.controller;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

import de.bastian.client.Application;
import de.bastian.client.view.UserView;

public class UserController extends Controller {

  private UserView userView;

  public UserController() {
    registerEventTypes(Application.Events.User.getType());
  }

  @Override
  protected void initialize() {
    userView = new UserView(this);
  }

  @Override
  public void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.User.getType()) {
      this.forwardToView(userView, event);
    }
  }

}
