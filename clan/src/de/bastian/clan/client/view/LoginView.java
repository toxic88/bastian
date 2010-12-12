package de.bastian.clan.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.events.LoginEvent;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class LoginView extends Composite {

    private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

    interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {}

    public LoginView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    TextBox email;

    @UiField
    PasswordTextBox password;

    @UiField
    Button button;

    @UiHandler("button")
    void onClickButton(ClickEvent e) {
        login();
    }

    private void login() {
        if (email.getText().equals("") || password.getText().equals("")) {
            return;
        }

        UserRequest request = Clan.REQUESTFACTORY.userRequest();
        request.login(email.getText(), password.getText()).fire(new AppReceiver<UserProxy>() {
            @Override
            public void onSuccess(UserProxy user) {
                if (user != null) {
                    Clan.EVENTBUS.fireEvent(new LoginEvent(user));
                    History.back();
                }
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: do something...
            }
        });
        password.setText("");
    }

    private void reset() {
        email.setText("");
        password.setText("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

}
