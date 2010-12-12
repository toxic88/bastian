package de.bastian.clan.client.mvp;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;

import de.bastian.clan.client.ClientFactory;

public abstract class AppActivity extends AbstractActivity {

    protected ClientFactory clientFactory;

    public AppActivity(Place place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

}
