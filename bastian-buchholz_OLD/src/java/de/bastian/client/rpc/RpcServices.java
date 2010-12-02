package de.bastian.client.rpc;

import com.google.gwt.core.client.GWT;

import de.bastian.client.rpc.services.UserManager;
import de.bastian.client.rpc.services.UserManagerAsync;

final public class RpcServices {

  private static RpcServices instance;

  private RpcServices() {
  }

  public static RpcServices get() {
    if (instance == null) {
      instance = new RpcServices();
    }
    return instance;
  }

  public UserManagerAsync getUserService() {
    return GWT.create(UserManager.class);
  }

}
