package de.bastian.clan.client.view.picture;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import de.bastian.clan.client.activity.picture.PicturesActivity;
import de.bastian.clan.client.view.widgets.HyperlinkPager;
import de.bastian.clan.shared.PictureProxy;

public class PicturesView extends Composite {

    private static PicturesViewUiBinder uiBinder = GWT.create(PicturesViewUiBinder.class);

    interface PicturesViewUiBinder extends UiBinder<Widget, PicturesView> {
    }

    public PicturesView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    FlowPanel content;

    @UiField
    HyperlinkPager pagerTop;

    @UiField
    HyperlinkPager pagerBottom;

    public void setPictures(List<PictureProxy> pictures, int page, int count) {
        for (PictureProxy picture : pictures) {
            content.add(new PictureView(picture));
        }

        pagerTop.setPrefix("pictures:");
        pagerTop.setPageSizeCount(page, PicturesActivity.pageSize, count);

        pagerBottom.setPrefix("pictures:");
        pagerBottom.setPageSizeCount(page, PicturesActivity.pageSize, count);
    }

    private void reset() {
        content.clear();
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        reset();
    }

}