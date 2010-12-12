package de.bastian.clan.client.activity.user;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.user.UserPlace;
import de.bastian.clan.client.view.user.UserView;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class UserActivity extends AppActivity {

    private Long userId;

    public UserActivity(UserPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        this.userId = place.getUserId();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final UserView userView = clientFactory.getUserView();

        if (userId != null) {
            UserRequest request = Clan.REQUESTFACTORY.userRequest();
            request.findUser(userId).fire(new AppReceiver<UserProxy>() {
                @Override
                public void onSuccess(UserProxy user) {
                    userView.setUser(user);
                    containerWidget.setWidget(userView.asWidget());
                }
            });
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
