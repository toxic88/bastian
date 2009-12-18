package de.bastian.db;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "false")
public class User implements Serializable {

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
  private List<String> rights;

  @Persistent
  @Column(allowsNull = "false")
  private Date createDate;

  public User() {
    List<String> r = new ArrayList<String>();
    r.add(de.bastian.client.model.User.USER);
    setRights(r);
    createDate = new Date();
  }

  public User(String firstName, String password) {
    this();
    setUsername(firstName);
    setPassword(password);
  }

  public User(String firstname, String password, String right) {
    this(firstname, password);
    addRight(right);
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
    this.password = User.passwordToHash(password);
  }

  public List<String> getRights() {
    return rights;
  }

  public boolean hasRight(String right) {
    if (rights.contains(right)) {
      return true;
    }
    return false;
  }

  public void setRights(List<String> rights) {
    this.rights = rights;
  }

  public void addRight(String right) {
    if (!rights.contains(right)) {
      rights.add(right);
    }
  }

  public Date getCreateDate() {
    return createDate;
  }

  public static byte[] passwordToHash(String password) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      return md5.digest(password.getBytes());
    } catch (Exception e) {
      return null;
    }
  }

  public boolean checkPassword(String password) {
    return Arrays.equals(this.password, User.passwordToHash(password));
  }

  public de.bastian.client.model.User getRpcUser() {
    return new de.bastian.client.model.User(getId(), getUsername(), getRights(), getCreateDate());
  }

}
