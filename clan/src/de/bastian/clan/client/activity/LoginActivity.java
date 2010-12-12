package de.bastian.clan.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.view.LoginView;

public class LoginActivity extends AppActivity {

    public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        LoginView loginView = clientFactory.getLoginView();
        containerWidget.setWidget(loginView.asWidget());
    }

}
