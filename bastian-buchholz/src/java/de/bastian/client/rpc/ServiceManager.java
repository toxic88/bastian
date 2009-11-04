package de.bastian.client.rpc;

import com.google.gwt.core.client.GWT;

import de.bastian.client.UserManager;
import de.bastian.client.UserManagerAsync;

final public class ServiceManager {

  private ServiceManager() {
  }

  public static UserManagerAsync getUserService() {
    return GWT.create(UserManager.class);
  }

}
