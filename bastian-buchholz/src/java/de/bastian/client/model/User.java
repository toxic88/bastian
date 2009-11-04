package de.bastian.client.model;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

public class User extends BaseModel {

  public User() {
  }

  public User(Long id, String username, Date createDate) {
    this.setId(id);
    this.setUsername(username);
    this.setCreateDate(createDate);
  }

  public Long getId() {
    return (Long) this.get("id");
  }

  public void setId(Long id) {
    this.set("id", id);
  }

  public String getUsername() {
    return (String) this.get("username");
  }

  public void setUsername(String username) {
    this.set("username", username);
  }

  public Date getCreateDate() {
    return (Date) this.get("createDate");
  }

  public void setCreateDate(Date createDate) {
    this.set("createDate", createDate);
  }

}
