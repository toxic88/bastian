package de.bastian.client.model;

import java.util.ArrayList;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.user.client.rpc.IsSerializable;

public class User extends BaseModel {

  public static enum Rights implements IsSerializable {
    Admin, User;
  }

  private Rights tmp;  /* Serialization issue */

  public User() {
    setId(new Long(-1));
    setCreateDate(new Date());
  }

  public User(Long id, String username, ArrayList<Rights> rights, Date createDate) {
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

  public ArrayList<Rights> getRights() {
    return (ArrayList<Rights>) get("rights");
  }

  public void setRights(ArrayList<Rights> rights) {
    set("rights", rights);
  }

  public boolean isAdmin() {
    return getRights().contains(Rights.Admin);
  }

  public boolean isUser() {
    return getRights().contains(Rights.User);
  }

  public Date getCreateDate() {
    return (Date) get("createDate");
  }

  public void setCreateDate(Date createDate) {
    set("createDate", createDate);
  }

}
