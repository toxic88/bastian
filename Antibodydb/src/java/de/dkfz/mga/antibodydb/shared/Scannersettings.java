package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;

public class Scannersettings implements Serializable {

  private long id;

  private String sensitivity;

  private String linearManual;

  private String brightness;

  private String contrast;

  private String signal;

  /* Getter / Setter */

  public Scannersettings() {
    
  }

  public String getSignal() {
    return signal;
  }

  public void setSignal(String signal) {
    this.signal = signal;
  }

  public String getBrightness() {
    return brightness;
  }

  public void setBrightness(String brightness) {
    this.brightness = brightness;
  }

  public String getContrast() {
    return contrast;
  }

  public void setContrast(String contrast) {
    this.contrast = contrast;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLinearManual() {
    return linearManual;
  }

  public void setLinearManual(String linearManual) {
    this.linearManual = linearManual;
  }

  public String getSensitivity() {
    return sensitivity;
  }

  public void setSensitivity(String sensitivity) {
    this.sensitivity = sensitivity;
  }

}
