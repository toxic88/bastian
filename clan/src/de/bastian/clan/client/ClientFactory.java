package de.bastian.clan.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import de.bastian.clan.client.forum.view.EditPostView;
import de.bastian.clan.client.forum.view.EditThemeView;
import de.bastian.clan.client.forum.view.PostsView;
import de.bastian.clan.client.forum.view.ThemesView;
import de.bastian.clan.client.forum.view.TopicView;
import de.bastian.clan.client.guestbook.view.GuestBookView;
import de.bastian.clan.client.picture.view.EditPictureView;
import de.bastian.clan.client.picture.view.PicturesView;
import de.bastian.clan.client.user.view.EditUserView;
import de.bastian.clan.client.user.view.UserView;
import de.bastian.clan.client.user.view.UsersView;
import de.bastian.clan.client.view.HelloView;
import de.bastian.clan.client.view.LoginView;

public interface ClientFactory {

    EventBus getEventBus();
    PlaceController getPlaceController();

    HelloView getHelloView();
    LoginView getLoginView();

    UsersView getUsersView();
    UserView getUserView();
    EditUserView getEditUserView();

    TopicView getTopicView();
    ThemesView getThemesView();
    EditThemeView getEditThemeView();
    PostsView getPostsView();
    EditPostView getEditPostView();

    PicturesView getPicturesView();
    EditPictureView getEditPictureView();

    GuestBookView getGuestBookView();

}
