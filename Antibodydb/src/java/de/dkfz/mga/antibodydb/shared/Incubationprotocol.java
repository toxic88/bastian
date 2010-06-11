package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;

public class Incubationprotocol implements Serializable {

  private long id;

  private String incubationprotocol;

  private String blocking;

  private String abIncubation1;

  private String abIncubation2;

  private String washing1;

  private String washing2;

  private long bufferset;

  /* Getter / Setter */

  public Incubationprotocol() {
    
  }

  public String getAbIncubation1() {
    return abIncubation1;
  }

  public void setAbIncubation1(String abIncubation1) {
    this.abIncubation1 = abIncubation1;
  }

  public String getAbIncubation2() {
    return abIncubation2;
  }

  public void setAbIncubation2(String abIncubation2) {
    this.abIncubation2 = abIncubation2;
  }

  public String getBlocking() {
    return blocking;
  }

  public void setBlocking(String blocking) {
    this.blocking = blocking;
  }

  public long getBufferset() {
    return bufferset;
  }

  public void setBufferset(long bufferset) {
    this.bufferset = bufferset;
  }

  public String getIncubationprotocol() {
    return incubationprotocol;
  }

  public void setIncubationprotocol(String incubationprotocol) {
    this.incubationprotocol = incubationprotocol;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getWashing1() {
    return washing1;
  }

  public void setWashing1(String washing1) {
    this.washing1 = washing1;
  }

  public String getWashing2() {
    return washing2;
  }

  public void setWashing2(String washing2) {
    this.washing2 = washing2;
  }

}
