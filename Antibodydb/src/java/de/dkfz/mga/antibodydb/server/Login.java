package de.dkfz.mga.antibodydb.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.dkfz.mga.antibodydb.client.services.LoginService;
import de.dkfz.mga.antibodydb.shared.User;
import javax.persistence.Persistence;

public class Login extends RemoteServiceServlet implements LoginService {

  public User login(String username, String password) {

    //Persistence.createEntityManagerFactory("AntibodyPU");

    return new User();
  }

  public void logout() {
    
  }

}
