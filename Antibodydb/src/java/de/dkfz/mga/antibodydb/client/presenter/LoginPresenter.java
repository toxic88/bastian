package de.dkfz.mga.antibodydb.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import de.dkfz.mga.antibodydb.client.Antibodydb;
import de.dkfz.mga.antibodydb.client.event.ErrorEvent;
import de.dkfz.mga.antibodydb.client.event.LoginEvent;
import de.dkfz.mga.antibodydb.client.event.LogoutEvent;
import de.dkfz.mga.antibodydb.client.event.LogoutEventHandler;
import de.dkfz.mga.antibodydb.client.view.LoginView;
import de.dkfz.mga.antibodydb.shared.User;

public class LoginPresenter implements Presenter {

  private final LoginView view;

  private static LoginPresenter instance = null;

  private LoginPresenter() {
    view = new LoginView(this);

    Antibodydb.EventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
      public void onLogout(LogoutEvent event) {
        AsyncCallback<Void> cb = new AsyncCallback<Void>() {
          public void onFailure(Throwable t) {
            Antibodydb.EventBus.fireEvent(new ErrorEvent(t));
          }
          public void onSuccess(Void result) {
            Antibodydb.User = null;
          }
        };
        Antibodydb.LoginService.logout(cb);
      }
    });

  }

  public void onLoginButtonClicked(String username, String password) {
    AsyncCallback<User> cb = new AsyncCallback<User>() {
      public void onFailure(Throwable t) {
        Antibodydb.EventBus.fireEvent(new ErrorEvent(t));
      }
      public void onSuccess(User result) {
        if (result != null) {
          Antibodydb.User = result;
          Antibodydb.EventBus.fireEvent(new LoginEvent(result));
        }
      }
    };
    Antibodydb.LoginService.login(username, password, cb);
  }

  public Widget asWidget() {
    return view;
  }

  public static LoginPresenter get() {
    if (instance == null) {
      instance = new LoginPresenter();
    }

      return instance;
    }

}
