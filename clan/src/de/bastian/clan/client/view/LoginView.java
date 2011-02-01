package de.bastian.clan.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.activity.LoginActivity;

public class LoginView extends Composite {

    public interface Presenter {
        void login(String email, String password);
    }

    private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

    interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {}

    private LoginActivity activity;

    public LoginView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    TextBox email;

    @UiField
    PasswordTextBox password;

    @UiField
    Button login;

    @UiHandler("login")
    void onLoginClick(ClickEvent e) {
        activity.login(email.getText(), password.getText());
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

    public void setActivity(LoginActivity activity) {
        this.activity = activity;
    }

}
