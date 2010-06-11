package de.dkfz.mga.antibodydb.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.dkfz.mga.antibodydb.shared.User;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
  public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

  private final User u;

  public LoginEvent(User u) {
    this.u = u;
  }

  public User getUser() {
    return u;
  }

  @Override
  public Type<LoginEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(LoginEventHandler handler) {
    handler.onLogin(this);
  }

}