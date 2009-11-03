package de.bastian.server;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import de.bastian.db.User;
import de.bastian.client.UserManager;
import de.bastian.server.rpc.RemoteServiceServlet;

public class UserManagerImpl extends RemoteServiceServlet implements UserManager {

  public boolean createUser(String username, String password) {
    PersistenceManager pm = this.getPersistenceManager();

    Query query = pm.newQuery(User.class, "username == usernameParam");
    query.declareParameters("String usernameParam");

    List<User> users = (List<User>) query.execute(username);

    if (users.size() > 0) {
      return false;
    }

    try {
      pm.makePersistent(new User(username, password));
    } catch (Exception e) {
      return false;
    } finally {
      pm.close();
    }
    return true;
  }

  public boolean updateUser(de.bastian.client.model.User updateUser) {
    PersistenceManager pm = this.getPersistenceManager();

    User user = pm.getObjectById(User.class, updateUser.getId());

    try {
      user.setUsername(updateUser.getUsername());
    } catch (Exception e) {
      return false;
    } finally {
      pm.close();
    }
    return true;
  }

  public boolean removeUser(Long id) {
    PersistenceManager pm = this.getPersistenceManager();

    User user = pm.getObjectById(User.class, id);

    try {
      pm.deletePersistent(user);
    } catch (Exception e) {
      return false;
    } finally {
      pm.close();
    }
    return true;
  }

  public boolean removeUser(de.bastian.client.model.User removeUser) {
    return this.removeUser(removeUser.getId());
  }

  public List<de.bastian.client.model.User> getAll() {
    PersistenceManager pm = this.getPersistenceManager();
    Query query = pm.newQuery(User.class);

    List ret = new ArrayList();
    try {
      List<User> users = (List<User>) query.execute();
      for (User user : users) {
        ret.add(new de.bastian.client.model.User(user.getId(), user.getUsername(), user.getCreateDate()));
      }
    } finally {
      pm.close();
    }

    return ret;
  }

  public boolean login(String username, String password) {
    PersistenceManager pm = this.getPersistenceManager();

    Query query = pm.newQuery(User.class, "username == usernameParam");
    query.declareParameters("String usernameParam");

    List<User> users = (List<User>) query.execute(username);

    for (User user : users) {
      if (user.checkPassword(password)) {
        this.getSession().setAttribute("user", user);
        return true;
      }
    }

    return false;
  }

}
