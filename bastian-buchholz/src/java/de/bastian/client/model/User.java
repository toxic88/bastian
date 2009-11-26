package de.bastian.client.model;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

public class User extends BaseModel {

  public User() {
  }

  public User(Long id, String username, Date createDate) {
    setId(id);
    setUsername(username);
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

  public Date getCreateDate() {
    return (Date) get("createDate");
  }

  public void setCreateDate(Date createDate) {
    set("createDate", createDate);
  }

}
