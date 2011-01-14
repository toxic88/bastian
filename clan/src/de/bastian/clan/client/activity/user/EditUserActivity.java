package de.bastian.clan.client.activity.user;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.user.EditUserPlace;
import de.bastian.clan.client.view.user.EditUserView;
import de.bastian.clan.shared.UserProxy;
import de.bastian.clan.shared.UserProxy.Type;
import de.bastian.clan.shared.UserRequest;

public class EditUserActivity extends AppActivity implements EditUserView.Presenter {

    private Long userId;

    public EditUserActivity(EditUserPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
         this.userId = place.getUserId();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final EditUserView editUserView = clientFactory.getEditUserView();
        editUserView.setActivity(this);

        if (userId != null) {
            UserRequest request = Clan.REQUESTFACTORY.userRequest();
            request.findUser(userId).fire(new AppReceiver<UserProxy>() {
                @Override
                public void onSuccess(UserProxy user) {
                    if (Clan.CURRENTUSER == null || (user != null && (user.getId() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
                        History.back();
                        return;
                    }

                    editUserView.setUser(user);
                    containerWidget.setWidget(editUserView.asWidget());
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

    @Override
    public void updateUser(UserProxy user, String firstname, String lastname, String email, String password, String steamid, Type type) {
        if (Clan.CURRENTUSER == null || (user != null && (user.getId() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
            History.back();
            return;
        }

        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()) {
            // TODO: show input errors
            return;
        }

        UserRequest request = Clan.REQUESTFACTORY.userRequest();

        if (user == null) {
            if (Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)) {
                user = request.create(UserProxy.class);
                user.setPassword(password);
            } else {
                History.back();
                return;
            }
        } else {
            user = request.edit(user);
            if (!password.isEmpty()) {
                user.setPassword(password);
            }
        }
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setSteamid(steamid);
        if (Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)) {
            user.setType(type);
        }

        request.persist().using(user).fire(new AppReceiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                History.back();
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: do something...
            }
        });
    }

}
