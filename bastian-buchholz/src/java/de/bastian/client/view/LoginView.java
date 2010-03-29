package de.bastian.client.view;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;

import de.bastian.client.Application;

public class LoginView extends View {

  private Window loginWindow;

  public LoginView(Controller controller) {
    super(controller);
  }

  @Override
  protected void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.Login.getType()) {
      loginWindow.show();
    }
  }

  @Override
  protected void initialize() {
    loginWindow = LoginViewUi.get();
  }

}
