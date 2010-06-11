package de.dkfz.mga.antibodydb.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ShowEventHandler extends EventHandler {

  void onShow(ShowEvent event);
  
}