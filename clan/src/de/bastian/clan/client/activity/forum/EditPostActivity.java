package de.bastian.clan.client.activity.forum;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.forum.EditPostPlace;
import de.bastian.clan.client.view.forum.EditPostView;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.TopicProxy;
import de.bastian.clan.shared.TopicRequest;
import de.bastian.clan.shared.UserProxy;


public class EditPostActivity extends AppActivity {

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

        if (topicId != null && themeId != null) {
            TopicRequest request = Clan.REQUESTFACTORY.topicRequest();

            request.findTopic(topicId).fire(new AppReceiver<TopicProxy>() {
                @Override
                public void onSuccess(final TopicProxy topic) {
                    PostRequest request = Clan.REQUESTFACTORY.postRequest();

                    request.findPost(themeId).fire(new AppReceiver<PostProxy>() {
                        @Override
                        public void onSuccess(final PostProxy theme) {
                            if (!theme.getTopic().equals(topic.getId())) { // could only be happen if the user change the historytoken
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
                                    if (!post.getTheme().equals(theme.getId())) { // could only be happen if the user change the historytoken
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
