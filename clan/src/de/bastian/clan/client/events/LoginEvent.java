package de.bastian.clan.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import de.bastian.clan.shared.UserProxy;

public class LoginEvent extends GwtEvent<LoginEvent.Handler> {

    public interface Handler extends EventHandler {
        void onLogin(LoginEvent e);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    private final UserProxy user;

    public LoginEvent(UserProxy user) {
        this.user = user;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    public UserProxy getUser() {
        return user;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onLogin(this);
    }

}
