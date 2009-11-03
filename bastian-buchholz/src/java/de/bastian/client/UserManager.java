package de.bastian.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.bastian.client.model.User;

@RemoteServiceRelativePath("user")
public interface UserManager extends RemoteService {

  public boolean createUser(String firstname, String password);

  public boolean updateUser(User updateUser);

  public boolean removeUser(Long id);
  
  public boolean removeUser(User user);

  public List<User> getAll();

  public boolean login(String username, String password);

}
