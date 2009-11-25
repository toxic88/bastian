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
    this.registerEventTypes(Application.Events.Init.getType());
    this.registerEventTypes(Application.Events.Error.getType());
  }

  @Override
  protected void initialize() {
    this.appView = new AppView(this);
  }

  @Override
  public void handleEvent(AppEvent event) {
    if (event.getType() == Application.Events.Init.getType()) {
      this.forwardToView(this.appView, event);
    } else if (event.getType() == Application.Events.Error.getType()) {

      Object data = event.getData();

      if (data instanceof Throwable) {
        event.setData(((RpcException) data).getError());
      }
      
      MessageBox.alert(Application.Messages.error(), event.<String>getData(), null);
    }
  }

}
