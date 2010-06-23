package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;

public class Images implements Serializable {

  private long id;

  private String image;

  private String signal;

  private long sds;

  private long scannersettings;

  private long lane;

  /* Getter / Setter */

  public Images() {
    
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public long getLane() {
    return lane;
  }

  public void setLane(long lane) {
    this.lane = lane;
  }

  public long getScannersettings() {
    return scannersettings;
  }

  public void setScannersettings(long scannersettings) {
    this.scannersettings = scannersettings;
  }

  public long getSds() {
    return sds;
  }

  public void setSds(long sds) {
    this.sds = sds;
  }

  public String getSignal() {
    return signal;
  }

  public void setSignal(String signal) {
    this.signal = signal;
  }

}
