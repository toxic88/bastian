package de.bastian.client.rpc.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.bastian.client.model.User;
import de.bastian.client.rpc.RpcException;

@RemoteServiceRelativePath("user")
public interface UserManager extends RemoteService {

  public User createUser(String firstname, String password) throws RpcException;

  public void updateUser(User updateUser) throws RpcException;

  public void removeUser(Long id) throws RpcException;
  
  public void removeUser(User user) throws RpcException;

  public ArrayList<User> getAll();

  public void login(String username, String password) throws RpcException;

}
