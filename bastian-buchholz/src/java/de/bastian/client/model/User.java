package de.bastian.client.model;

import java.util.Date;
import com.extjs.gxt.ui.client.data.BaseModel;

public class User extends BaseModel {

  public User() {
  }

  public User(String username, Date createDate) {
    this.setUsername(username);
    this.setCreateDate(createDate);
  }

  public void setUsername(String username) {
    this.set("username", username);
  }

  public void setCreateDate(Date createDate) {
    this.set("createDate", createDate);
  }

  public String getUsername() {
    return (String) this.get("username");
  }

  public Date getCreateDate() {
    return (Date) this.get("createDate");
  }

}
