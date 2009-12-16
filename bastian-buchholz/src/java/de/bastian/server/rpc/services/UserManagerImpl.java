package de.bastian.server.rpc.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import de.bastian.client.Application;
import de.bastian.client.rpc.services.UserManager;
import de.bastian.client.rpc.RpcException;
import de.bastian.db.User;
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

    PersistenceManager pm = getPersistenceManager();

    Query query = pm.newQuery(User.class, "username == usernameParam");
    query.declareParameters("String usernameParam");

    List<User> users = (List<User>) query.execute(username);

    if (users.size() > 0) {
      return true;
    }
    return false;
  }

  public de.bastian.client.model.User createUser(String username, String password) throws RpcException {
    if (checkUsername(username)) {
      throw new RpcException("The user '" + username + "' allready exists!");
    }

    PersistenceManager pm = getPersistenceManager();

    try {
      User user = new User(username, password);
      pm.makePersistent(user);
      return user.getRpcUser();
    } catch (Exception e) {
      throw new RpcException("Failed to create user '" + username + "'!");
    } finally {
      pm.close();
    }
  }

  public void updateUser(de.bastian.client.model.User updateUser) throws RpcException {
    if (checkUsername(updateUser.getUsername())) {
      throw new RpcException("The user '" + updateUser.getUsername() + "' allready exists!");
    }

    PersistenceManager pm = getPersistenceManager();

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
    PersistenceManager pm = getPersistenceManager();

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
    removeUser(removeUser.getId());
  }

  public List<de.bastian.client.model.User> getAll() {
    PersistenceManager pm = getPersistenceManager();

    Query query = pm.newQuery(User.class);

    List ret = new ArrayList();
    try {
      List<User> users = (List<User>) pm.detachCopyAll((Collection<?>) query.execute());

      for (User user : users) {
        ret.add(user.getRpcUser());
      }
    } finally {
      pm.close();
    }

    return ret;
  }

  public void login(String username, String password) throws RpcException {
    PersistenceManager pm = getPersistenceManager();

    Query query = pm.newQuery(User.class, "username == usernameParam");
    query.declareParameters("String usernameParam");

    List<User> users = (List<User>) query.execute(username);

    for (User user : users) {
      if (user.checkPassword(password)) {
        getSession().setAttribute(Application.Keys.SESSION_NAME, user);
        addCookie(Application.Keys.COOKIE_NAME, user.toString());
        return;
      }
    }

    throw new RpcException("Wrong username or password!");
  }

  public void logout() {
    getSession().removeAttribute(Application.Keys.SESSION_NAME);
    removeCookie(Application.Keys.COOKIE_NAME);
  }

}
