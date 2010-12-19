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
import de.bastian.clan.client.place.forum.EditThemePlace;
import de.bastian.clan.client.view.forum.EditThemeView;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.TopicProxy;
import de.bastian.clan.shared.TopicRequest;
import de.bastian.clan.shared.UserProxy;


public class EditThemeActivity extends AppActivity {

    private Long topicId;
    private Long themeId;

    public EditThemeActivity(EditThemePlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        topicId = place.getTopicId();
        themeId = place.getThemeId();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final EditThemeView editThemeView = clientFactory.getEditThemeView();

        if (topicId != null) {
            TopicRequest request = Clan.REQUESTFACTORY.topicRequest();

            request.findTopic(topicId).fire(new AppReceiver<TopicProxy>() {
                @Override
                public void onSuccess(final TopicProxy topic) {
                    if (themeId == null) {
                        editThemeView.setTheme(topic, null);
                        containerWidget.setWidget(editThemeView.asWidget());
                        return;
                    }

                    PostRequest request = Clan.REQUESTFACTORY.postRequest();

                    request.findPost(themeId).fire(new AppReceiver<PostProxy>() {
                        @Override
                        public void onSuccess(PostProxy theme) {
                            if (!theme.getTopic().equals(topic.getId())) { // could only be happen if the user change the historytoken
                                History.back();
                                return;
                            }

                            if (Clan.CURRENTUSER == null || (theme != null && (theme.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
                                History.back();
                                return;
                            }

                            editThemeView.setTheme(topic, theme);
                            containerWidget.setWidget(editThemeView.asWidget());
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
