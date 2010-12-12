package de.bastian.clan.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
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

public class ClientFactoryImpl implements ClientFactory {

    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeController = new PlaceController(eventBus);

    private static final HelloView helloView = new HelloView();
    private static final LoginView loginView = new LoginView();

    private static final UsersView usersView = new UsersView();
    private static final UserView userView = new UserView();
    private static final EditUserView userDetailView = new EditUserView();

    private static final TopicView topicView = new TopicView();
    private static final ThemesView themesView = new ThemesView();
    private static final EditThemeView editThemeView = new EditThemeView();
    private static final PostsView postsView = new PostsView();
    private static final EditPostView editPostView = new EditPostView();

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }


    @Override
    public HelloView getHelloView() {
        return helloView;
    }

    @Override
    public LoginView getLoginView() {
        return loginView;
    }


    @Override
    public UsersView getUsersView() {
        return usersView;
    }

    @Override
    public UserView getUserView() {
        return userView;
    }

    @Override
    public EditUserView getUserDetailView() {
        return userDetailView;
    }


    @Override
    public TopicView getTopicView() {
        return topicView;
    }

    @Override
    public ThemesView getThemesView() {
        return themesView;
    }

    @Override
    public EditThemeView getEditThemeView() {
        return editThemeView;
    }

    @Override
    public PostsView getPostsView() {
        return postsView;
    }

    @Override
    public EditPostView getEditPostView() {
        return editPostView;
    }

}
