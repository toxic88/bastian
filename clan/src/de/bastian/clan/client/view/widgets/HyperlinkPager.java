package de.bastian.clan.client.view.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Widget;

public class HyperlinkPager extends Composite {

    private static HyperlinkPagerUiBinder uiBinder = GWT.create(HyperlinkPagerUiBinder.class);

    interface HyperlinkPagerUiBinder extends UiBinder<Widget, HyperlinkPager> {}

    private String prefix = "";

    public HyperlinkPager() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    FlowPanel content;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setPageSizeCount(int currentPage, int perPage, int count) {
        int pages = (int) Math.ceil((double)count / perPage) - 1;

        content.clear();

        content.add(new InlineHyperlink("<", prefix + ( currentPage - 1 < 0 ? 0 : currentPage - 1 )));
        addSpacer();

        for (int i = 0; i <= pages; i++) {
            if (i == currentPage) {
                content.add(new InlineHTML("" + (i + 1)));
            } else {
                content.add(new InlineHyperlink("" + (i + 1), prefix + i));
            }
            addSpacer();
        }

        content.add(new InlineHyperlink(">", prefix + ( currentPage + 1 > pages ? pages : currentPage + 1 )));
    }

    private void addSpacer() {
        content.add(new InlineHTML(" "));
    }

}
