package de.dkfz.mga.antibodydb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.dkfz.mga.antibodydb.client.presenter.LoginPresenter;

public class LoginView extends Composite {

  interface LoginViewUiBinder extends UiBinder<HTMLPanel, LoginView> { }

  private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

  private LoginPresenter presenter = null;

  @UiField Button button;
  @UiField InputElement username;
  @UiField InputElement password;

  public LoginView(LoginPresenter presenter) {
    initWidget(uiBinder.createAndBindUi(this));
    this.presenter = presenter;
  }

  @UiHandler("button")
  void handleClick(ClickEvent e) {
    if (presenter != null)  {
      presenter.onLoginButtonClicked(username.getValue(), password.getValue());
    }
  }

}
