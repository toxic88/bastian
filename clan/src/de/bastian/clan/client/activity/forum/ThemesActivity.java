package de.bastian.clan.client.activity.forum;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.forum.ThemesPlace;
import de.bastian.clan.client.view.forum.ThemesView;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.PostRequest;
import de.bastian.clan.shared.TopicProxy;
import de.bastian.clan.shared.TopicRequest;

public class ThemesActivity extends AppActivity {

    public static final int pageSize = 10;

    private Long topicId;
    private int page;

    public ThemesActivity(ThemesPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        topicId = place.getTopicId();
        page = place.getPage();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final ThemesView themesView = clientFactory.getThemesView();

        TopicRequest request = Clan.REQUESTFACTORY.topicRequest();

        request.findTopic(topicId).fire(new AppReceiver<TopicProxy>() {
            @Override
            public void onSuccess(final TopicProxy topic) {
                if (topic == null) {
                    History.back();
                    return;
                }

                PostRequest request = Clan.REQUESTFACTORY.postRequest();

                request.findThemes(topicId, pageSize * page, pageSize * page + pageSize).fire(new AppReceiver<List<PostProxy>>() {
                    @Override
                    public void onSuccess(final List<PostProxy> themes) {
                        if (themes.size() == 0 && page != 0) {
                            History.back();
                            return;
                        }

                        PostRequest request = Clan.REQUESTFACTORY.postRequest();

                        request.countThemes(topic.getId()).fire(new AppReceiver<Integer>() {
                            @Override
                            public void onSuccess(Integer count) {
                                themesView.setThemes(topic, themes, page, count);
                                containerWidget.setWidget(themesView.asWidget());
                            }
                        });
                    }
                });
            }
        });
    }

}
