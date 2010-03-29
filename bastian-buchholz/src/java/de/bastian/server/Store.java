package de.bastian.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import de.bastian.client.model.User.Rights;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class Store {

  private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

  public static PersistenceManagerFactory getPersistenceManagerFactory() {
    return pmfInstance;
  }

  public static PersistenceManager getPersistenceManager() {
    return getPersistenceManagerFactory().getPersistenceManager();
  }


  /**
   * User
   */
  @PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "false")
  public static class User implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    @Column(allowsNull = "false")
    private String username;

    @Persistent
    @Column(allowsNull = "false")
    private byte[] password;

    @Persistent
    private ArrayList<Rights> rights;

    @Persistent
    @Column(allowsNull = "false")
    private Date createDate;

    public User() {
      ArrayList<Rights> r = new ArrayList<Rights>();
      r.add(Rights.User);
      setRights(r);
      createDate = new Date();
    }

    public User(String firstName, String password) {
      this();
      setUsername(firstName);
      setPassword(password);
    }

    public User(String firstname, String password, ArrayList<Rights> rights) {
      this(firstname, password);
      setRights(rights);
    }

    public Long getId() {
      return id;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public byte[] getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = Store.stringToHash(password);
    }

    public ArrayList<Rights> getRights() {
      return rights;
    }

    public boolean hasRight(Rights right) {
      if (rights.contains(right)) {
        return true;
      }
      return false;
    }

    public void setRights(ArrayList<Rights> rights) {
      this.rights = rights;
    }

    public void addRight(Rights right) {
      if (!rights.contains(right)) {
        rights.add(right);
      }
    }

    public void removeRight(Rights right) {
      if (rights.contains(right)) {
        rights.remove(right);
      }
    }

    public Date getCreateDate() {
      return createDate;
    }

    public boolean checkPassword(String password) {
      return Arrays.equals(this.password, Store.stringToHash(password));
    }

    public de.bastian.client.model.User getRpcUser() {
      return new de.bastian.client.model.User(getId(), getUsername(), getRights(), getCreateDate());
    }

  }

  public static byte[] stringToHash(String text) {
    text += "random salt!";
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      return md5.digest(text.getBytes());
    } catch (Exception e) {
      return null;
    }
  }

}
