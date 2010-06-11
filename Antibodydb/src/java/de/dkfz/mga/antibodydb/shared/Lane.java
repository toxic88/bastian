package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Lane implements Serializable {

  private long id;

  private ArrayList<String> lysateProtein;

  private ArrayList<String> totalProtein;

  /* Getter / Setter */

  public Lane() {
    
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ArrayList<String> getLysateProtein() {
    return lysateProtein;
  }

  public void setLysateProtein(ArrayList<String> lysateProtein) {
    this.lysateProtein = lysateProtein;
  }

  public ArrayList<String> getTotalProtein() {
    return totalProtein;
  }

  public void setTotalProtein(ArrayList<String> totalProtein) {
    this.totalProtein = totalProtein;
  }

}
