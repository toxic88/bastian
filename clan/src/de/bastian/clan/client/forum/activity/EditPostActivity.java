package de.bastian.clan.client.forum.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.forum.place.EditPostPlace;
import de.bastian.clan.client.forum.view.EditPostView;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.TopicProxy;
import de.bastian.clan.shared.TopicRequest;
import de.bastian.clan.shared.UserProxy;

public class EditPostActivity extends AppActivity implements EditPostView.Presenter {

    private Long topicId;
    private Long themeId;
    private Long postId;

    public EditPostActivity(EditPostPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        topicId = place.getTopicId();
        themeId = place.getThemeId();
        postId = place.getPostId();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final EditPostView editPostView = clientFactory.getEditPostView();
        editPostView.setActivity(this);

        TopicRequest request = Clan.REQUESTFACTORY.topicRequest();

        request.findTopic(topicId).fire(new AppReceiver<TopicProxy>() {
            @Override
            public void onSuccess(final TopicProxy topic) {
                if (topic == null) {
                    History.back();
                    return;
                }

                PostRequest request = Clan.REQUESTFACTORY.postRequest();

                request.findPost(themeId).fire(new AppReceiver<PostProxy>() {
                    @Override
                    public void onSuccess(final PostProxy theme) {
                        if (theme == null || (theme != null && !theme.getTopic().equals(topic.getId()))) { // could only be happen if the user change the historytoken
                            History.back();
                            return;
                        }

                        if (postId == null) {
                            editPostView.setPost(topic, theme, null);
                            containerWidget.setWidget(editPostView.asWidget());
                            return;
                        }

                        PostRequest request = Clan.REQUESTFACTORY.postRequest();

                        request.findPost(postId).fire(new AppReceiver<PostProxy>() {
                            @Override
                            public void onSuccess(PostProxy post) {
                                if (post != null && !post.getTheme().equals(theme.getId())) { // could only be happen if the user change the historytoken
                                    History.back();
                                    return;
                                }

                                if (Clan.CURRENTUSER == null || (post != null && (post.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
                                    History.back();
                                    return;
                                }

                                editPostView.setPost(topic, theme, post);
                                containerWidget.setWidget(editPostView.asWidget());
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void updatePost(TopicProxy topic, PostProxy theme, PostProxy post, String title, String text) {
        if (Clan.CURRENTUSER == null || (post != null && (post.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
            History.back();
            return;
        }

        if (topic == null || theme == null || title.isEmpty() || text.isEmpty()) {
            // TODO: show input errors
            return;
        }

        PostRequest request = Clan.REQUESTFACTORY.postRequest();

        if (post == null) {
            post = request.create(PostProxy.class);
            post.setTopic(topic.getId());
            post.setTheme(theme.getId());
            post.setUser(Clan.CURRENTUSER.getId());
        } else {
            post = request.edit(post);
        }
        post.setTitle(title);
        post.setText(text);

        request.persist().using(post).fire(new AppReceiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                History.back();
            }
            @Override
            public void onFailure(ServerFailure error) {
                // TODO: do something....
            }
        });
    }

}
