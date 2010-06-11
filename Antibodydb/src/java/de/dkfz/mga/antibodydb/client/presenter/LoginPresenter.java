package de.dkfz.mga.antibodydb.client.presenter;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import de.dkfz.mga.antibodydb.client.Antibodydb;
import de.dkfz.mga.antibodydb.client.event.LoginEvent;
import de.dkfz.mga.antibodydb.client.view.LoginView;
import de.dkfz.mga.antibodydb.shared.User;

public class LoginPresenter implements Presenter {

  private final LoginView view;

  private static LoginPresenter instance = null;

  private LoginPresenter() {
    view = new LoginView(this);
  }

  public void onLoginButtonClicked(String username, String password) {
    AsyncCallback<User> cb = new AsyncCallback<User>() {

      public void onFailure(Throwable caught) {

      }

      public void onSuccess(User result) {
        if (result != null) {
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
    if (LoginPresenter.instance == null) {
      LoginPresenter.instance = new LoginPresenter();
    }

    return LoginPresenter.instance;
  }

}
