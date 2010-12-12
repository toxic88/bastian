package de.bastian.clan.client.activity.user;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.user.EditUserPlace;
import de.bastian.clan.client.view.user.EditUserView;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserRequest;

public class EditUserActivity extends AppActivity {

    private Long userId;

    public EditUserActivity(EditUserPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
         this.userId = place.getUserId();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final EditUserView userDetailView = clientFactory.getUserDetailView();

        if (userId != null) {
            UserRequest request = Clan.REQUESTFACTORY.userRequest();
            request.findUser(userId).fire(new AppReceiver<UserProxy>() {
                @Override
                public void onSuccess(UserProxy user) {
                    userDetailView.setUser(user);
                    containerWidget.setWidget(userDetailView.asWidget());
                }
            });
        } else {
            userDetailView.setUser(null);
            containerWidget.setWidget(userDetailView.asWidget());
        }
    }

}
