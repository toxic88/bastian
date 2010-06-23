package de.dkfz.mga.antibodydb.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ErrorEventHandler extends EventHandler {

  void onError(ErrorEvent event);

}