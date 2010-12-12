package de.bastian.clan.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import de.bastian.clan.client.view.HelloView;
import de.bastian.clan.client.view.LoginView;
import de.bastian.clan.client.view.forum.EditPostView;
import de.bastian.clan.client.view.forum.EditThemeView;
import de.bastian.clan.client.view.forum.PostsView;
import de.bastian.clan.client.view.forum.ThemesView;
import de.bastian.clan.client.view.forum.TopicView;
import de.bastian.clan.client.view.user.EditUserView;
import de.bastian.clan.client.view.user.UserView;
import de.bastian.clan.client.view.user.UsersView;

public interface ClientFactory {

    EventBus getEventBus();
    PlaceController getPlaceController();

    HelloView getHelloView();
    LoginView getLoginView();

    UsersView getUsersView();
    UserView getUserView();
    EditUserView getUserDetailView();

    TopicView getTopicView();
    ThemesView getThemesView();
    EditThemeView getEditThemeView();
    PostsView getPostsView();
    EditPostView getEditPostView();

}
