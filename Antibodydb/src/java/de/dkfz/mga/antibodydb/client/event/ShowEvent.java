package de.dkfz.mga.antibodydb.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.dkfz.mga.antibodydb.client.presenter.Presenter;

public class ShowEvent extends GwtEvent<ShowEventHandler> {
  public static Type<ShowEventHandler> TYPE = new Type<ShowEventHandler>();

  private final Presenter p;

  public ShowEvent(Presenter p) {
    this.p = p;
  }

  public Presenter getPresenter() {
    return p;
  }

  @Override
  public Type<ShowEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ShowEventHandler handler) {
    handler.onShow(this);
  }

}