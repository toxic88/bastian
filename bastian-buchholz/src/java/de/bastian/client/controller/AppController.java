package de.bastian.client.controller;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.MessageBox;

import de.bastian.client.Application;
import de.bastian.client.rpc.RpcException;
import de.bastian.client.view.AppView;

public class AppController extends Controller {

  private AppView appView;

  public AppController() {
    registerEventTypes(Application.Events.Init.getType());
    registerEventTypes(Application.Events.Error.getType());
    registerEventTypes(Application.Events.LoggedIn.getType());
    registerEventTypes(Application.Events.LoggedOut.getType());
  }

  @Override
  protected void initialize() {
    appView = new AppView(this);
  }

  @Override
  public void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.Init.getType()) {
      forwardToView(appView, event);
    } else if (event.getType() == Application.Events.Error.getType()) {

      Object data = event.getData();

      if (data instanceof Throwable) {
        event.setData( ((RpcException) data).getError() );
      }
      
      MessageBox.alert(Application.Messages.error(), event.<String>getData(), null);
    } else if (event.getType() == Application.Events.LoggedIn.getType()) {
      Application.setLoggedIn(true);
    } else if (event.getType() == Application.Events.LoggedOut.getType()) {
      Application.setLoggedIn(false);
    }
  }

}
