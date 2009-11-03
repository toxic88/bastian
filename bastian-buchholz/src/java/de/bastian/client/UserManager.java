package de.bastian.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.bastian.client.model.User;

@RemoteServiceRelativePath("user")
public interface UserManager extends RemoteService {

  public boolean login(String username, String password);

  public boolean createUser(String firstname, String password);

  public List<User> getAll();

}
