package de.bastian.client.view;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import de.bastian.client.widget.LoginWindow;
import de.bastian.client.AppEvents;

public class LoginView extends View {

  private Window loginWindow;

  public LoginView(Controller controller) {
    super(controller);
  }

  @Override
  protected void handleEvent(AppEvent event) {
    if (event.getType() == AppEvents.Login) {
      this.loginWindow.show();
    }
  }

  @Override
  protected void initialize() {
    this.loginWindow = LoginWindow.get();
  }

}
