package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;

public class Targetprotein implements Serializable {

  private long id;

  private String targetprotein;

  private String mwkd;

  private String swissProtAccsession;

  private String supplier;

  private String stock;

  private String references;

  /* Getter / Setter */

  public Targetprotein() {
    
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMwkd() {
    return mwkd;
  }

  public void setMwkd(String mwkd) {
    this.mwkd = mwkd;
  }

  public String getReferences() {
    return references;
  }

  public void setReferences(String references) {
      this.references = references;
  }

  public String getStock() {
    return stock;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getSwissProtAccsession() {
    return swissProtAccsession;
  }

  public void setSwissProtAccsession(String swissProtAccsession) {
    this.swissProtAccsession = swissProtAccsession;
  }

  public String getTargetprotein() {
    return targetprotein;
  }

  public void setTargetprotein(String targetprotein) {
    this.targetprotein = targetprotein;
  }

}
