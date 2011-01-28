package de.bastian.clan.client.forum.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.forum.place.EditThemePlace;
import de.bastian.clan.client.forum.view.EditThemeView;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.TopicProxy;
import de.bastian.clan.shared.TopicRequest;
import de.bastian.clan.shared.UserProxy;

public class EditThemeActivity extends AppActivity implements EditThemeView.Presenter {

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
        editThemeView.setActivity(this);

        TopicRequest request = Clan.REQUESTFACTORY.topicRequest();

        request.findTopic(topicId).fire(new AppReceiver<TopicProxy>() {
            @Override
            public void onSuccess(final TopicProxy topic) {
                if (topic == null) {
                    History.back();
                    return;
                }

                if (themeId == null) {
                    editThemeView.setTheme(topic, null);
                    containerWidget.setWidget(editThemeView.asWidget());
                    return;
                }

                PostRequest request = Clan.REQUESTFACTORY.postRequest();

                request.findPost(themeId).fire(new AppReceiver<PostProxy>() {
                    @Override
                    public void onSuccess(PostProxy theme) {
                        if (theme != null && !theme.getTopic().equals(topic.getId())) { // could only be happen if the user change the historytoken
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
    }

    @Override
    public void updateTheme(TopicProxy topic, PostProxy theme, String title, String text) {
        if (Clan.CURRENTUSER == null || (theme != null && (theme.getUser() != Clan.CURRENTUSER.getId() && !Clan.CURRENTUSER.getType().equals(UserProxy.Type.Admin)))) {
            History.back();
            return;
        }

        if (topic == null || title.isEmpty() || text.isEmpty()) {
            // TODO: show input errors
            return;
        }

        PostRequest request = Clan.REQUESTFACTORY.postRequest();

        if (theme == null) {
            theme = request.create(PostProxy.class);
            theme.setTopic(topic.getId());
            theme.setUser(Clan.CURRENTUSER.getId());
        } else {
            theme = request.edit(theme);
        }
        theme.setTitle(title);
        theme.setText(text);

        request.persist().using(theme).fire(new AppReceiver<Void>() {
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
