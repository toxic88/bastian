package de.bastian.clan.client.activity.picture;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.client.place.picture.PicturesPlace;
import de.bastian.clan.client.view.picture.PicturesView;
import de.bastian.clan.shared.PictureProxy;
import de.bastian.clan.shared.PictureRequest;

public class PicturesActivity extends AppActivity {

    public static final int pageSize = 10;

    private int page;

    public PicturesActivity(PicturesPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        page = place.getPage();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final PicturesView picturesView = clientFactory.getPicturesView();

        PictureRequest request = Clan.REQUESTFACTORY.pictureRequest();

        request.findPictures(pageSize * page, pageSize * page + pageSize).fire(new AppReceiver<List<PictureProxy>>() {
            @Override
            public void onSuccess(final List<PictureProxy> pictures) {
                if (pictures.size() == 0 && page != 0) {
                    History.back();
                    return;
                }

                PictureRequest request = Clan.REQUESTFACTORY.pictureRequest();

                request.countPictures().fire(new AppReceiver<Integer>() {
                    @Override
                    public void onSuccess(Integer count) {
                        picturesView.setPictures(pictures, page, count);
                        containerWidget.setWidget(picturesView.asWidget());
                    }
                });
            }
        });
    }

}