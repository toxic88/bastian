package de.bastian.client.controller;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import de.bastian.client.AppEvents;
import de.bastian.client.view.LoginView;

public class LoginController extends Controller {

  private LoginView loginView;

  public LoginController() {
    this.registerEventTypes(AppEvents.Login);
  }

  @Override
  protected void initialize() {
    this.loginView = new LoginView(this);
  }

  @Override
  public void handleEvent(AppEvent event) {
    if (event.getType() == AppEvents.Login) {
      this.forwardToView(this.loginView, event);
    }
  }

}
