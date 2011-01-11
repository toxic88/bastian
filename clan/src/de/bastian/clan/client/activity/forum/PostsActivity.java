package de.bastian.clan.client.activity.forum;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.forum.PostsPlace;
import de.bastian.clan.client.view.forum.PostView;
import de.bastian.clan.client.view.forum.PostsView;
import de.bastian.clan.client.view.widgets.ConfirmPopupPanel;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.TopicProxy;
import de.bastian.clan.shared.TopicRequest;

public class PostsActivity extends AppActivity implements PostView.Presenter {

    public static final int pageSize = 10;

    private Long themeId;
    private int page;

    public PostsActivity(PostsPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        themeId = place.getThemeId();
        page = place.getPage();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final PostsView postsView = clientFactory.getPostsView();
        postsView.setActivity(this);

        PostRequest request = Clan.REQUESTFACTORY.postRequest();

        request.findPost(themeId).fire(new AppReceiver<PostProxy>() {
            @Override
            public void onSuccess(final PostProxy parent) {
                if (parent == null) {
                    History.back();
                    return;
                }

                TopicRequest request = Clan.REQUESTFACTORY.topicRequest();

                request.findTopic(parent.getTopic()).fire(new AppReceiver<TopicProxy>() {
                    @Override
                    public void onSuccess(final TopicProxy topic) {
                        PostRequest request = Clan.REQUESTFACTORY.postRequest();

                        request.findPosts(parent.getId(), pageSize * page, pageSize * page + pageSize).fire(new AppReceiver<List<PostProxy>>() {
                            @Override
                            public void onSuccess(final List<PostProxy> posts) {
                                if (posts.size() == 0 && page != 0) {
                                    History.back();
                                    return;
                                }

                                PostRequest request = Clan.REQUESTFACTORY.postRequest();

                                request.countPosts(themeId).fire(new AppReceiver<Integer>() {
                                    @Override
                                    public void onSuccess(Integer count) {
                                        postsView.setPosts(topic, parent, posts, page, ++count);
                                        containerWidget.setWidget(postsView.asWidget());
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void removePost(PostProxy post) {
        PostRequest request = Clan.REQUESTFACTORY.postRequest();

        request.remove().using(request.edit(post)).fire(new AppReceiver<Void>() {
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
