package de.bastian.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.core.XDOM;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.fx.FxConfig;
import com.extjs.gxt.ui.client.mvc.Dispatcher;

import de.bastian.client.controller.AppController;
import de.bastian.client.controller.LoginController;
import de.bastian.client.icons.AppIcons;

public class Application implements EntryPoint {

  /**
   * Messages
   */
  public static AppMessages Messages = GWT.create(AppMessages.class);

  /**
   * Icons
   */
  public static AppIcons Icons = GWT.create(AppIcons.class);

  /**
   * Application events
   */
  public static enum Events {
    Init, Login, Error;

    private EventType eventType;

    Events() {
      this.eventType = new EventType();
    }

    public EventType getType() {
      return this.eventType;
    }

    @Override
    public String toString() {
      return this.name();
    }

  }

  /**
   * Registry keys
   */
  public class Keys {

    public static final String VIEWPORT_CENTER = "viewport_center";

    public static final String VIEWPORT_WEST = "viewport_west";

  }

  public void onModuleLoad() {
    final Dispatcher dispatcher = Dispatcher.get();

    /**
     * History support
     */
    History.addValueChangeHandler(new ValueChangeHandler<String>() {

      private String lastToken = null;

      public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token.equals(this.lastToken)) {
          return;
        }
        this.lastToken = token;

        if (token.equals(Application.Events.Login.toString())) {
          dispatcher.dispatch(Application.Events.Login.getType());
        }

      }

    });

    /**
     * Controllers
     */
    dispatcher.addController(new AppController());
    dispatcher.addController(new LoginController());

    dispatcher.dispatch(Application.Events.Init.getType());

    History.fireCurrentHistoryState();

    /**
     * Fade out the overlay
     */
    new El(XDOM.getElementById("loading-mask")).fadeOut(FxConfig.NONE);
  }

}
