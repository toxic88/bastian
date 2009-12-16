package de.bastian.client.model;

import java.util.List;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

public class User extends BaseModel {

  final public static int ADMIN = 1;
  final public static int USER = 2;

  public User() {
    setId(new Long(-1));
    setCreateDate(new Date());
  }

  public User(Long id, String username, List<Integer> rights, Date createDate) {
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

  public List<Integer> getRights() {
    return (List<Integer>) get("rights");
  }

  public void setRights(List<Integer> rights) {
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
