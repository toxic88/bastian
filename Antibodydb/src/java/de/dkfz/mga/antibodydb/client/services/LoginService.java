package de.dkfz.mga.antibodydb.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.dkfz.mga.antibodydb.shared.User;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
    
  public User login(String username, String password);

  public void logout();

}
