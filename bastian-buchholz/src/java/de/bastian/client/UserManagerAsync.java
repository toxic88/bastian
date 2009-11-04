package de.bastian.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.bastian.client.model.User;

public interface UserManagerAsync {

  public void createUser(String firstname, String password, AsyncCallback<Void> callback);

  public void updateUser(User updateUser, AsyncCallback<Void> asyncCallback);

  public void removeUser(Long id, AsyncCallback<Void> asyncCallback);

  public void removeUser(User user, AsyncCallback<Void> asyncCallback);

  public void getAll(AsyncCallback<List<User>> callback);

  public void login(String username, String password, AsyncCallback<Void> asyncCallback);

}
