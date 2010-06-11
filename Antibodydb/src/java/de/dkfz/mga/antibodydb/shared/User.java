package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

  private long id;

  private String username;

  private ArrayList<String> rights;

  /* Getter / Setter */

  public User() {
    
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ArrayList<String> getRights() {
    return rights;
  }

  public void setRights(ArrayList<String> rights) {
    this.rights = rights;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
