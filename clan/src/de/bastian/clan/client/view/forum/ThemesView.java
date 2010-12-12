package de.bastian.clan.client.view.forum;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.activity.forum.ThemesActivity;
import de.bastian.clan.client.view.widgets.HyperlinkPager;
import de.bastian.clan.shared.PostProxy;
import de.bastian.clan.shared.TopicProxy;

public class ThemesView extends Composite {

    private static ThemesViewUiBinder uiBinder = GWT.create(ThemesViewUiBinder.class);

    interface ThemesViewUiBinder extends UiBinder<Widget, ThemesView> {}

    public static interface ThemesViewConstants extends Constants {
        String forum();
        String noThemesPosted();
    }

    interface Style extends CssResource {
        String post();
    }

    private static class ThemesCell extends AbstractCell<PostProxy> {

        private String style;

        public void setStyle(String style) {
            this.style = style;
        }

        @Override
        public void render(Context context, PostProxy post, SafeHtmlBuilder sb) {
            if (post == null) {
                return;
            }

            sb.appendHtmlConstant("<div class='" + style + "'>");
                sb.appendHtmlConstant("<a href='#posts:" + post.getId() + "'>");
                    sb.appendEscaped(post.getTitle());
                sb.appendHtmlConstant("</a>");
                sb.appendHtmlConstant("<div>");
                    sb.appendEscaped(post.getText().substring(0, Math.min(70, post.getText().length())));
                sb.appendHtmlConstant("</div>");
            sb.appendHtmlConstant("</div>");
        }

    }

    public ThemesView() {
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
    InlineHyperlink newThemeTop;

    @UiField
    InlineHyperlink newThemeBottom;

    @UiFactory CellList<PostProxy> getCellList() {
        final ThemesCell tc = new ThemesCell();

        CellList<PostProxy> cl = new CellList<PostProxy>(tc);
        cl.setEmptyListMessage(SafeHtmlUtils.fromString(Clan.MESSAGES.noThemesPosted()));
        cl.setSelectionModel(new NoSelectionModel<PostProxy>());

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                tc.setStyle(style.post());
            }
        });
        return cl;
    }

    public void setThemes(TopicProxy topic, List<PostProxy> posts, int page, int count) {
        header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > " + topic.getName());

        content.setRowCount(count, true);
        content.setRowData(0, posts);


        pagerTop.setPrefix("themes:" + topic.getId() + ":");
        pagerTop.setPageSizeCount(page, ThemesActivity.pageSize, count);

        pagerBottom.setPrefix("themes:" + topic.getId() + ":");
        pagerBottom.setPageSizeCount(page, ThemesActivity.pageSize, count);

        newThemeTop.setTargetHistoryToken("editTheme:" + topic.getId());
        newThemeBottom.setTargetHistoryToken("editTheme:" + topic.getId());
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
