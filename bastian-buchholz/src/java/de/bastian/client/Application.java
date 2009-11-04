package de.bastian.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.fx.FxConfig;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

import de.bastian.client.controller.AppController;
import de.bastian.client.controller.LoginController;

public class Application implements EntryPoint {

  public static AppMessages MESSAGES = (AppMessages) GWT.create(AppMessages.class);

  public void onModuleLoad() {

    final Dispatcher dispatcher = Dispatcher.get();

    History.addValueChangeHandler(new ValueChangeHandler<String>() {

      private String lastToken = null;

      public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token.equals(this.lastToken)) {
          return;
        }
        this.lastToken = token;

        if (token.equals(AppEvents.Tokens.Login)) {
          dispatcher.dispatch(AppEvents.Login);
        }

      }

    });

    /**
     * Registry
     */
    Registry.register(AppRegistryKeys.SERVICES_USER, GWT.create(UserManager.class));

    /**
     * Controllers
     */
    dispatcher.addController(new AppController());
    dispatcher.addController(new LoginController());

    dispatcher.dispatch(AppEvents.Init);

    History.fireCurrentHistoryState();
    new El(XDOM.getElementById("loading-mask")).fadeOut(FxConfig.NONE);
  }

}
