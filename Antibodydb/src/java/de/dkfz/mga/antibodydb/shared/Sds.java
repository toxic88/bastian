package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;

public class Sds implements Serializable {

  private long id;

  private String sds;

  private String acrylamid;

  private String sep;

  private String voltage;

  private String size;

  /* Getter / Setter */

  public Sds() {
    
  }

  public String getAcrylamid() {
    return acrylamid;
  }

  public void setAcrylamid(String acrylamid) {
    this.acrylamid = acrylamid;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSds() {
    return sds;
  }

  public void setSds(String sds) {
    this.sds = sds;
  }

  public String getSep() {
    return sep;
  }

  public void setSep(String sep) {
    this.sep = sep;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getVoltage() {
    return voltage;
  }

  public void setVoltage(String voltage) {
    this.voltage = voltage;
  }

}
