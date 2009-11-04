package de.bastian.server;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import de.bastian.db.User;
import de.bastian.client.UserManager;
import de.bastian.client.rpc.RpcException;
import de.bastian.server.rpc.RemoteServiceServlet;

public class UserManagerImpl extends RemoteServiceServlet implements UserManager {

  /**
   * Returns true if the user exists!
   * @param String username
   * @return boolean
   */
  private boolean checkUsername(String username) {
    if (username == null || username.equals("")) {
      return true;
    }

    PersistenceManager pm = this.getPersistenceManager();

    Query query = pm.newQuery(User.class, "username == usernameParam");
    query.declareParameters("String usernameParam");

    List<User> users = (List<User>) query.execute(username);

    if (users.size() > 0) {
      return true;
    }
    return false;
  }

  public void createUser(String username, String password) throws RpcException {
    if (this.checkUsername(username)) {
      throw new RpcException("The user '" + username + "' allready exists!");
    }

    PersistenceManager pm = this.getPersistenceManager();

    try {
      pm.makePersistent(new User(username, password));
    } catch (Exception e) {
      throw new RpcException("Failed to create user '" + username + "'!");
    } finally {
      pm.close();
    }
  }

  public void updateUser(de.bastian.client.model.User updateUser) throws RpcException {
    if (this.checkUsername(updateUser.getUsername())) {
      throw new RpcException("The user '" + updateUser.getUsername() + "' allready exists!");
    }

    PersistenceManager pm = this.getPersistenceManager();

    try {
      User user = pm.getObjectById(User.class, updateUser.getId());

      user.setUsername(updateUser.getUsername());
    } catch (Exception e) {
      throw new RpcException("Failed to update user '" + updateUser.getUsername()  + "'!");
    } finally {
      pm.close();
    }
  }

  public void removeUser(Long id) throws RpcException {
    PersistenceManager pm = this.getPersistenceManager();

    try {
      User user = pm.getObjectById(User.class, id);

      pm.deletePersistent(user);
    } catch (Exception e) {
      throw new RpcException("Failed to remove user!");
    } finally {
      pm.close();
    }
  }

  public void removeUser(de.bastian.client.model.User removeUser) throws RpcException {
    this.removeUser(removeUser.getId());
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

  public void login(String username, String password) throws RpcException {
    PersistenceManager pm = this.getPersistenceManager();

    Query query = pm.newQuery(User.class, "username == usernameParam");
    query.declareParameters("String usernameParam");

    List<User> users = (List<User>) query.execute(username);

    for (User user : users) {
      if (user.checkPassword(password)) {
        this.getSession().setAttribute("user", user);
      }
    }

    throw new RpcException("Wrong username or password!");
  }

}
