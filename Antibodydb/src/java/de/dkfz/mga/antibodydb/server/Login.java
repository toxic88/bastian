package de.dkfz.mga.antibodydb.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.dkfz.mga.antibodydb.client.services.LoginService;

public class Login extends RemoteServiceServlet implements LoginService {

  public de.dkfz.mga.antibodydb.shared.User login(String username, String password) {

    /*PersistenceManagerFactory emf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    PersistenceManager em = emf.getPersistenceManager();

    User user = em.getObjectById(User.class, 10);

    de.dkfz.mga.antibodydb.shared.User ret = new de.dkfz.mga.antibodydb.shared.User();
    ret.setUsername(user.getUsername());

    return ret;*/
    return new de.dkfz.mga.antibodydb.shared.User();
  }

  public void logout() {
    
  }

}
