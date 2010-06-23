package de.dkfz.mga.antibodydb.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ErrorEvent extends GwtEvent<ErrorEventHandler> {
  public static Type<ErrorEventHandler> TYPE = new Type<ErrorEventHandler>();

  private final Throwable t;

  public ErrorEvent(Throwable t) {
    this.t = t;
  }

  public Throwable getError() {
    return t;
  }

  @Override
  public Type<ErrorEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ErrorEventHandler handler) {
    handler.onError(this);
  }

}