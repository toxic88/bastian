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
        final EditUserView editUserView = clientFactory.getEditUserView();

        if (userId != null) {
            UserRequest request = Clan.REQUESTFACTORY.userRequest();
            request.findUser(userId).fire(new AppReceiver<UserProxy>() {
                @Override
                public void onSuccess(UserProxy user) {
                    if (Clan.CURRENTUSER != null && (user.getId() == Clan.CURRENTUSER.getId() || Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin))) {
                        editUserView.setUser(user);
                        containerWidget.setWidget(editUserView.asWidget());
                    } else {
                        History.back();
                    }
                }
            });
        } else if (Clan.CURRENTUSER != null && Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)) {
            editUserView.setUser(null);
            containerWidget.setWidget(editUserView.asWidget());
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
