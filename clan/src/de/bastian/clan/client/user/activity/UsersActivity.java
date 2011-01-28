package de.bastian.clan.client.user.activity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.user.place.UsersPlace;
import de.bastian.clan.client.user.view.UsersView;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class UsersActivity extends AppActivity {

    public UsersActivity(UsersPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final UsersView usersView = clientFactory.getUsersView();

        UserRequest request = Clan.REQUESTFACTORY.userRequest();
        request.findAll().fire(new AppReceiver<List<UserProxy>>() {
            @Override
            public void onSuccess(List<UserProxy> users) {
                usersView.setUsers(users);
                containerWidget.setWidget(usersView.asWidget());
            }
        });
    }

}
