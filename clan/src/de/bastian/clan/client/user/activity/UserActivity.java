package de.bastian.clan.client.user.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.user.place.UserPlace;
import de.bastian.clan.client.user.view.UserView;
import de.bastian.clan.client.view.widgets.ConfirmPopupPanel;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class UserActivity extends AppActivity implements UserView.Presenter {

    private Long userId;

    public UserActivity(UserPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        this.userId = place.getUserId();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final UserView userView = clientFactory.getUserView();
        userView.setActivity(this);

        UserRequest request = Clan.REQUESTFACTORY.userRequest();
        request.findUser(userId).fire(new AppReceiver<UserProxy>() {
            @Override
            public void onSuccess(UserProxy user) {
                if (user == null) {
                    History.back();
                    return;
                }

                userView.setUser(user);
                containerWidget.setWidget(userView.asWidget());
            }
        });
    }

    @Override
    public void removeUser(UserProxy user) {
        UserRequest request = Clan.REQUESTFACTORY.userRequest();

        request.remove().using(request.edit(user)).fire(new AppReceiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                ConfirmPopupPanel.get().hide();
                History.fireCurrentHistoryState();
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: do something...
            }
        });
    }

}
