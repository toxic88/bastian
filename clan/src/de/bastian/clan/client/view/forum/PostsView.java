package de.bastian.clan.client.view.forum;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.activity.forum.PostsActivity;
import de.bastian.clan.client.view.widgets.HyperlinkPager;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.TopicProxy;

public class PostsView extends Composite {

    private static PostsViewUiBinder uiBinder = GWT.create(PostsViewUiBinder.class);

    interface PostsViewUiBinder extends UiBinder<Widget, PostsView> {}

    private PostsActivity activity;

    public PostsView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    HeadingElement header;

    @UiField
    FlowPanel content;

    @UiField
    HyperlinkPager pagerTop;

    @UiField
    HyperlinkPager pagerBottom;

    @UiField
    InlineHyperlink newPostTop;

    @UiField
    InlineHyperlink newPostBottom;

    public void setPosts(TopicProxy topic, PostProxy theme, List<PostProxy> posts, int page, int count) {
        header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > " + theme.getTitle());

        if (page == 0) { // add the theme as first post
            posts.add(0, theme);
        }

        for (PostProxy post : posts) {
            content.add(new PostView(post, activity));
        }

        pagerTop.setPrefix("posts:" + theme.getId() + ":");
        pagerTop.setPageSizeCount(page, PostsActivity.pageSize, count - 1);

        pagerBottom.setPrefix("posts:" + theme.getId() + ":");
        pagerBottom.setPageSizeCount(page, PostsActivity.pageSize, count - 1);

        newPostTop.setTargetHistoryToken("editPost:" + topic.getId() + ":" + theme.getId());
        newPostBottom.setTargetHistoryToken("editPost:" + topic.getId() + ":" + theme.getId());
    }

    private void reset() {
        header.setInnerHTML("");
        content.clear();
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

    public void setActivity(PostsActivity activity) {
        this.activity = activity;
    }

}
