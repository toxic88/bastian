package de.bastian.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
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
import de.bastian.client.controller.TimetableController;
import de.bastian.client.controller.UserController;
import de.bastian.client.resources.Resources;
import de.bastian.client.rpc.RpcServices;

public class Application implements EntryPoint {

  /**
   * Messages
   */
  public static AppMessages Messages = GWT.create(AppMessages.class);

  /**
   * Icons
   */
  public static Resources Resources = GWT.create(Resources.class);

  /**
   * RPC Services
   */
  public static RpcServices Services = RpcServices.get();

  /**
   * Application events
   */
  public static enum Events {
    Init, Error,                              // Main application events
    Login(true), User(true), TimeTable(true), // Component events
    LoggedIn, LoggedOut;                      // Logical events

    private EventType eventType = new EventType();

    private boolean historyEvent = false;

    Events() {
    }

    Events(boolean historyEvent) {
      this.historyEvent = historyEvent;
    }

    public EventType getType() {
      return eventType;
    }

    public boolean isHistoryEvent() {
      return historyEvent;
    }

  }

  /**
   * Registry keys
   */
  public class Keys {

    public static final String VIEWPORT_CENTER = "viewport_center";

    public static final String VIEWPORT_WEST = "viewport_west";

    public static final String SESSION_NAME = "ee11cbb19052e40b07aac0ca060c23ee";

    public static final String COOKIE_NAME = "900cff6e4532927ffe090f2aa0590cee";

  }

  public static boolean isLoggedIn() {
    return Cookies.getCookie(Application.Keys.COOKIE_NAME) != null;
  }

  public void onModuleLoad() {
    /**
     * Add stylesheet
     */
    Application.Resources.applicationCss().ensureInjected();

    final Dispatcher dispatcher = Dispatcher.get();

    /**
     * History support
     */
    History.addValueChangeHandler(new ValueChangeHandler<String>() {

      private String lastToken = null;

      public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token.equals(lastToken)) {
          return;
        }
        lastToken = token;

        try {
          Events e = Application.Events.valueOf(token);
          if (e.isHistoryEvent()) {
            dispatcher.dispatch(e.getType());
          }
        } catch(Exception e) {
        }

      }

    });

    /**
     * Add the controllers
     */
    dispatcher.addController(new AppController());
    dispatcher.addController(new LoginController());
    dispatcher.addController(new UserController());
    dispatcher.addController(new TimetableController());

    /**
     * Dispatch the Init event
     */
    dispatcher.dispatch(Application.Events.Init.getType());

    /**
     * If a token allready exists fire the ValueChangeHandler
     */
    History.fireCurrentHistoryState();

    /**
     * Fade out the overlay
     */
    El.fly(XDOM.getElementById("loading-mask")).fadeOut(FxConfig.NONE);
  }

}
