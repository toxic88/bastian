package de.bastian.db;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

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
  @Column(allowsNull = "false")
  private Date createDate;

  public User() {
    this.createDate = new Date();
  }

  public User(String firstName, String password) {
    this();
    this.setUsername(firstName);
    this.setPassword(password);
  }

  public Long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public byte[] getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = User.passwordToHash(password);
  }

  public Date getCreateDate() {
    return this.createDate;
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

}
