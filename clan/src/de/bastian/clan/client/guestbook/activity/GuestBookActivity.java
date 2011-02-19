package de.bastian.clan.client.guestbook.activity;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.bastian.clan.client.Clan;
import de.bastian.clan.client.ClientFactory;
import de.bastian.clan.client.guestbook.place.GuestBookPlace;
import de.bastian.clan.client.guestbook.view.GuestBookView;
import de.bastian.clan.client.mvp.AppActivity;
import de.bastian.clan.client.mvp.AppReceiver;
import de.bastian.clan.shared.GuestBookEntryProxy;
import de.bastian.clan.shared.GuestBookEntryRequest;

public class GuestBookActivity extends AppActivity {

    public static final int pageSize = 20;

    private int page;

    public GuestBookActivity(GuestBookPlace place, ClientFactory clientFactory) {
        super(place, clientFactory);
        page = place.getPage();
    }

    @Override
    public void start(final AcceptsOneWidget containerWidget, EventBus eventBus) {
        final GuestBookView guestBookView = clientFactory.getGuestBookView();
        containerWidget.setWidget(guestBookView);

        GuestBookEntryRequest request = Clan.REQUESTFACTORY.guestBookEntryRequest();

        request.findGuestBookEntrys(0, 20).fire(new AppReceiver<List<GuestBookEntryProxy>>() {
            @Override
            public void onSuccess(List<GuestBookEntryProxy> guestBookEntries) {
                guestBookView.setGuestBookEntries(guestBookEntries);
            }
        });
    }

}
