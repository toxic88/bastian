package de.bastian.client.rpc.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.bastian.client.model.User;

public interface UserManagerAsync {

  public void createUser(String firstname, String password, AsyncCallback<User> callback);

  public void updateUser(User updateUser, AsyncCallback<Void> asyncCallback);

  public void removeUser(Long id, AsyncCallback<Void> asyncCallback);

  public void removeUser(User user, AsyncCallback<Void> asyncCallback);

  public void getAll(AsyncCallback<ArrayList<User>> callback);

  public void login(String username, String password, AsyncCallback<Void> asyncCallback);

}
