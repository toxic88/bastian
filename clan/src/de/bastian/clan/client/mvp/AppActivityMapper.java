package de.bastian.clan.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.place.shared.Place;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.activity.HelloActivity;
import de.bastian.clan.client.activity.LoginActivity;
import de.bastian.clan.client.forum.activity.EditPostActivity;
import de.bastian.clan.client.forum.activity.EditThemeActivity;
import de.bastian.clan.client.forum.activity.PostsActivity;
import de.bastian.clan.client.forum.activity.ThemesActivity;
import de.bastian.clan.client.forum.activity.TopicActivity;
import de.bastian.clan.client.forum.place.EditPostPlace;
import de.bastian.clan.client.forum.place.EditThemePlace;
import de.bastian.clan.client.forum.place.PostsPlace;
import de.bastian.clan.client.forum.place.ThemesPlace;
import de.bastian.clan.client.forum.place.TopicPlace;
import de.bastian.clan.client.guestbook.activity.GuestBookActivity;
import de.bastian.clan.client.guestbook.place.GuestBookPlace;
import de.bastian.clan.client.picture.activity.EditPictureActivity;
import de.bastian.clan.client.picture.activity.PicturesActivity;
import de.bastian.clan.client.picture.place.EditPicturePlace;
import de.bastian.clan.client.picture.place.PicturesPlace;
import de.bastian.clan.client.place.HelloPlace;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.user.activity.EditUserActivity;
import de.bastian.clan.client.user.activity.UserActivity;
import de.bastian.clan.client.user.activity.UsersActivity;
import de.bastian.clan.client.user.place.EditUserPlace;
import de.bastian.clan.client.user.place.UserPlace;
import de.bastian.clan.client.user.place.UsersPlace;

public class AppActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof HelloPlace) {
            return new HelloActivity(place, clientFactory);
        }
        if (place instanceof LoginPlace) {
            return new LoginActivity((LoginPlace) place, clientFactory);
        }

        if (place instanceof UserPlace) {
            return new UserActivity((UserPlace) place, clientFactory);
        }
        if (place instanceof UsersPlace) {
            return new UsersActivity((UsersPlace) place, clientFactory);
        }

        if (place instanceof TopicPlace) {
            return new TopicActivity((TopicPlace) place, clientFactory);
        }
        if (place instanceof ThemesPlace) {
            return new ThemesActivity((ThemesPlace) place, clientFactory);
        }
        if (place instanceof PostsPlace) {
            return new PostsActivity((PostsPlace) place, clientFactory);
        }

        if (place instanceof PicturesPlace) {
            return new PicturesActivity((PicturesPlace) place, clientFactory);
        }

        if (place instanceof GuestBookPlace) {
            return new GuestBookActivity((GuestBookPlace) place, clientFactory);
        }

        if (Clan.CURRENTUSER != null) {
            if (place instanceof EditUserPlace) {
                return new EditUserActivity((EditUserPlace) place, clientFactory);
            }

            if (place instanceof EditThemePlace) {
                return new EditThemeActivity((EditThemePlace) place, clientFactory);
            }
            if (place instanceof EditPostPlace) {
                return new EditPostActivity((EditPostPlace) place, clientFactory);
            }

            if (place instanceof EditPicturePlace) {
                return new EditPictureActivity((EditPicturePlace) place, clientFactory);
            }
        }

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                Clan.PLACECONTROLLER.goTo(new LoginPlace());
            }
        });

        return null;
    }

}
