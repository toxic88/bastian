package de.dkfz.mga.antibodydb.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LogoutEventHandler extends EventHandler {

  void onLogout(LogoutEvent event);

}