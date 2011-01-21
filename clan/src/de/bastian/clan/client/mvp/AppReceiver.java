package de.bastian.clan.client.mvp;

import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.requestfactory.shared.ServerFailure;
import com.google.gwt.user.client.History;

public abstract class AppReceiver<V> extends Receiver<V> {

    @Override
    public void onFailure(ServerFailure error) {
        /**
         * Default action on failure
         * @TODO: display error!
         */
        History.back();
    }

}
