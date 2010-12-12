package de.bastian.clan.client.view.forum;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.activity.forum.PostsActivity;
import de.bastian.clan.client.view.widgets.HyperlinkPager;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.TopicProxy;

public class PostsView extends Composite {

    private static PostsViewUiBinder uiBinder = GWT.create(PostsViewUiBinder.class);

    interface PostsViewUiBinder extends UiBinder<Widget, PostsView> {}

    interface Style extends CssResource {
        String post();
    }

    private static class PostsCell extends AbstractCell<PostProxy> {

        private String style;

        public void setStyle(String style) {
            this.style = style;
        }

        @Override
        public void render(Context context, PostProxy post, SafeHtmlBuilder sb) {
            if (post == null) {
                return;
            }

            sb.appendHtmlConstant("<div class='" + style + "'><table><tr>");
            sb.appendHtmlConstant("<td width='150px' rowspan='2'>");
                sb.appendEscaped("User...");
            sb.appendHtmlConstant("</td><th>");
                sb.appendEscaped(post.getTitle());
            sb.appendHtmlConstant("</th></tr><tr><td>");
                sb.appendEscaped(post.getText());
            sb.appendHtmlConstant("</td></tr></table></div>");
        }

    }

    public PostsView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    Style style;

    @UiField
    HeadingElement header;

    @UiField
    CellList<PostProxy> content;

    @UiField
    HyperlinkPager pagerTop;

    @UiField
    HyperlinkPager pagerBottom;

    @UiField
    InlineHyperlink newPostTop;

    @UiField
    InlineHyperlink newPostBottom;

    @UiFactory CellList<PostProxy> getCellList() {
        final PostsCell tc = new PostsCell();

        CellList<PostProxy> cl = new CellList<PostProxy>(tc);

        cl.setSelectionModel(new NoSelectionModel<PostProxy>());

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                tc.setStyle(style.post());
            }
        });
        return cl;
    }

    public void setPosts(TopicProxy topic, PostProxy theme, List<PostProxy> posts, int page, int count) {
        header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > <a href='#themes:" + topic.getId() + "'>" + topic.getName() + "</a> > " + theme.getTitle());

        if (page == 0) { // add the theme as first post
            posts.add(0, theme);
            count++;
        }

        content.setRowCount(count, true);
        content.setRowData(0, posts);

        pagerTop.setPrefix("posts:" + theme.getId() + ":");
        pagerTop.setPageSizeCount(page, PostsActivity.pageSize, count);

        pagerBottom.setPrefix("posts:" + theme.getId() + ":");
        pagerBottom.setPageSizeCount(page, PostsActivity.pageSize, count);

        newPostTop.setTargetHistoryToken("editPost:" + topic.getId() + ":" + theme.getId());
        newPostBottom.setTargetHistoryToken("editPost:" + topic.getId() + ":" + theme.getId());
    }

    private void reset() {
        header.setInnerHTML("");
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

}
