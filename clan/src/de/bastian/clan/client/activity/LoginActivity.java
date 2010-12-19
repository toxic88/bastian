package de.bastian.clan.client.activity;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
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
        if (Clan.CURRENTUSER == null) {
            LoginView loginView = clientFactory.getLoginView();
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

}
