package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;

public class Bufferset implements Serializable {

  private long id;

  private String bufferset;

  private String washbuffer;

  private String abIncubation1;

  private String abIncubation2;

  private String blocking;

  /* Getter / Setter */

  public Bufferset() {
    
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

  public String getBufferset() {
    return bufferset;
  }

  public void setBufferset(String bufferset) {
    this.bufferset = bufferset;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getid() {
    return id;
  }

  public String getWashbuffer() {
    return washbuffer;
  }

  public void setWashbuffer(String washbuffer) {
    this.washbuffer = washbuffer;
  }

}
