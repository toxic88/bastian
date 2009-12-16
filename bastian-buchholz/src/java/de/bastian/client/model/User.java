package de.bastian.client.model;

import java.util.List;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

public class User extends BaseModel {

  final public static String ADMIN = "admin";
  final public static String USER = "user";

  public User() {
    setId(new Long(-1));
    setCreateDate(new Date());
  }

  public User(Long id, String username, List<String> rights, Date createDate) {
    this();
    setId(id);
    setUsername(username);
    setRights(rights);
    setCreateDate(createDate);
  }

  public Long getId() {
    return (Long) get("id");
  }

  public void setId(Long id) {
    set("id", id);
  }

  public String getUsername() {
    return (String) get("username");
  }

  public void setUsername(String username) {
    set("username", username);
  }

  public List<String> getRights() {
    return (List<String>) get("rights");
  }

  public void setRights(List<String> rights) {
    set("rights", rights);
  }

  public boolean isAdmin() {
    return getRights().contains(ADMIN);
  }

  public boolean isUser() {
    return getRights().contains(USER);
  }

  public Date getCreateDate() {
    return (Date) get("createDate");
  }

  public void setCreateDate(Date createDate) {
    set("createDate", createDate);
  }

}
