package de.bastian.clan.client.forum.activity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.forum.place.TopicPlace;
import de.bastian.clan.client.forum.view.TopicView;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.TopicProxy;
import de.bastian.clan.shared.TopicRequest;

public class TopicActivity extends AppActivity {

    public TopicActivity(TopicPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final TopicView topicView = clientFactory.getTopicView();

        TopicRequest request = Clan.REQUESTFACTORY.topicRequest();
        request.findAll().fire(new AppReceiver<List<TopicProxy>>() {
            @Override
            public void onSuccess(List<TopicProxy> topics) {
                topicView.setTopics(topics);
                containerWidget.setWidget(topicView);
            }
        });
    }

}
