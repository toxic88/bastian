package de.dkfz.mga.antibodydb.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.dkfz.mga.antibodydb.shared.User;

public interface LoginServiceAsync {
    
  public void login(String username, String password, AsyncCallback<User> callback);

  public void logout(AsyncCallback<Void> callback);

}
