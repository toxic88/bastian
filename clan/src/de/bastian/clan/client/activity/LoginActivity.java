package de.bastian.clan.client.activity;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.events.LoginEvent;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.view.LoginView;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class LoginActivity extends AppActivity implements LoginView.Presenter {

    public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        if (Clan.CURRENTUSER == null) {
            LoginView loginView = clientFactory.getLoginView();
            loginView.setActivity(this);
            containerWidget.setWidget(loginView.asWidget());
        } else {
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                @Override
                public void execute() {
                    History.back();
                }
            });
        }
    }

    @Override
    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            // TODO: show input errors
            return;
        }

        UserRequest request = Clan.REQUESTFACTORY.userRequest();

        request.login(email, password).fire(new AppReceiver<UserProxy>() {
            @Override
            public void onSuccess(UserProxy user) {
                if (user != null) {
                    Clan.EVENTBUS.fireEvent(new LoginEvent(user));
                    History.back();
                }
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: show input errors
            }
        });
    }

}
