package de.bastian.clan.client.view.forum;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;

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
    }

    public ThemesView() {
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
    InlineHyperlink newThemeTop;

    @UiField
    InlineHyperlink newThemeBottom;

    public void setThemes(TopicProxy topic, List<PostProxy> themes, int page, int count) {
        header.setInnerHTML("<a href='#forum:'>" + Clan.MESSAGES.forum() + "</a> > " + topic.getName());

        for (PostProxy theme : themes) {
            content.add(new ThemeView(theme));
        }

        pagerTop.setPrefix("themes:" + topic.getId() + ":");
        pagerTop.setPageSizeCount(page, ThemesActivity.pageSize, count);

        pagerBottom.setPrefix("themes:" + topic.getId() + ":");
        pagerBottom.setPageSizeCount(page, ThemesActivity.pageSize, count);

        newThemeTop.setTargetHistoryToken("editTheme:" + topic.getId());
        newThemeBottom.setTargetHistoryToken("editTheme:" + topic.getId());
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

}
