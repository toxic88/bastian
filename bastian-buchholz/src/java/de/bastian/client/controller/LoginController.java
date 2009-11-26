package de.bastian.client.controller;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

import de.bastian.client.Application;
import de.bastian.client.view.LoginView;

public class LoginController extends Controller {

  private LoginView loginView;

  public LoginController() {
    registerEventTypes(Application.Events.Login.getType());
  }

  @Override
  protected void initialize() {
    loginView = new LoginView(this);
  }

  @Override
  public void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.Login.getType()) {
      this.forwardToView(loginView, event);
    }
  }

}
