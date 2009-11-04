package de.bastian.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.bastian.client.model.User;
import de.bastian.client.rpc.RpcException;

@RemoteServiceRelativePath("user")
public interface UserManager extends RemoteService {

  public void createUser(String firstname, String password) throws RpcException;

  public void updateUser(User updateUser) throws RpcException;

  public void removeUser(Long id) throws RpcException;
  
  public void removeUser(User user) throws RpcException;

  public List<User> getAll();

  public void login(String username, String password) throws RpcException;

}
