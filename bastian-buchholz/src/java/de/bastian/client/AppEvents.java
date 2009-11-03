package de.bastian.client;

import com.extjs.gxt.ui.client.event.EventType;

public class AppEvents {

  public static final EventType Init = new EventType();

  public static final EventType Login = new EventType();

  public static final EventType Error = new EventType();

  public class Tokens {

    public static final String Login = "Login";

  }

}
