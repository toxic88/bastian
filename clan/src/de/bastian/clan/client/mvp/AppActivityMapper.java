package de.bastian.clan.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.activity.HelloActivity;
import de.bastian.clan.client.activity.LoginActivity;
import de.bastian.clan.client.activity.forum.EditPostActivity;
import de.bastian.clan.client.activity.forum.EditThemeActivity;
import de.bastian.clan.client.activity.forum.PostsActivity;
import de.bastian.clan.client.activity.forum.ThemesActivity;
import de.bastian.clan.client.activity.forum.TopicActivity;
import de.bastian.clan.client.activity.user.EditUserActivity;
import de.bastian.clan.client.activity.user.UserActivity;
import de.bastian.clan.client.activity.user.UsersActivity;
import de.bastian.clan.client.place.HelloPlace;
import de.bastian.clan.client.place.LoginPlace;
import de.bastian.clan.client.place.forum.EditPostPlace;
import de.bastian.clan.client.place.forum.EditThemePlace;
import de.bastian.clan.client.place.forum.PostsPlace;
import de.bastian.clan.client.place.forum.ThemesPlace;
import de.bastian.clan.client.place.forum.TopicPlace;
import de.bastian.clan.client.place.user.EditUserPlace;
import de.bastian.clan.client.place.user.UserPlace;
import de.bastian.clan.client.place.user.UsersPlace;

public class AppActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        // TODO: check if user is logged in or not for some places
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
        if (place instanceof EditUserPlace) {
            return new EditUserActivity((EditUserPlace) place, clientFactory);
        }

        if (place instanceof TopicPlace) {
            return new TopicActivity((TopicPlace) place, clientFactory);
        }
        if (place instanceof ThemesPlace) {
            return new ThemesActivity((ThemesPlace) place, clientFactory);
        }
        if (place instanceof EditThemePlace) {
            return new EditThemeActivity((EditThemePlace) place, clientFactory);
        }
        if (place instanceof PostsPlace) {
            return new PostsActivity((PostsPlace) place, clientFactory);
        }
        if (place instanceof EditPostPlace) {
            return new EditPostActivity((EditPostPlace) place, clientFactory);
        }

        return null;
    }

}
