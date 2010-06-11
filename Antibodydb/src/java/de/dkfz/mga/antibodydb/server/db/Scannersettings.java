/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dkfz.mga.antibodydb.server.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author bastian
 */
@Entity
@Table(name = "DEV_T_Scannersettings", catalog = "phpwebdb", schema = "dbo")
@NamedQueries({
  @NamedQuery(name = "Scannersettings.findAll", query = "SELECT s FROM Scannersettings s"),
  @NamedQuery(name = "Scannersettings.findById", query = "SELECT s FROM Scannersettings s WHERE s.id = :id"),
  @NamedQuery(name = "Scannersettings.findBySensitivity", query = "SELECT s FROM Scannersettings s WHERE s.sensitivity = :sensitivity"),
  @NamedQuery(name = "Scannersettings.findByLinearManual", query = "SELECT s FROM Scannersettings s WHERE s.linearManual = :linearManual"),
  @NamedQuery(name = "Scannersettings.findByBrightness", query = "SELECT s FROM Scannersettings s WHERE s.brightness = :brightness"),
  @NamedQuery(name = "Scannersettings.findByContrast", query = "SELECT s FROM Scannersettings s WHERE s.contrast = :contrast"),
  @NamedQuery(name = "Scannersettings.findBySignal", query = "SELECT s FROM Scannersettings s WHERE s.signal = :signal")})
public class Scannersettings implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Column(name = "Sensitivity")
  private String sensitivity;
  @Column(name = "Linear_Manual")
  private String linearManual;
  @Column(name = "Brightness")
  private String brightness;
  @Column(name = "Contrast")
  private String contrast;
  @Column(name = "Signal")
  private String signal;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "fsTScannersettingsid")
  private List<Images> imagesList;

  public Scannersettings() {
  }

  public Scannersettings(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSensitivity() {
    return sensitivity;
  }

  public void setSensitivity(String sensitivity) {
    this.sensitivity = sensitivity;
  }

  public String getLinearManual() {
    return linearManual;
  }

  public void setLinearManual(String linearManual) {
    this.linearManual = linearManual;
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

  public String getSignal() {
    return signal;
  }

  public void setSignal(String signal) {
    this.signal = signal;
  }

  public List<Images> getImagesList() {
    return imagesList;
  }

  public void setImagesList(List<Images> imagesList) {
    this.imagesList = imagesList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Scannersettings)) {
      return false;
    }
    Scannersettings other = (Scannersettings) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "de.dkfz.mga.antibodydb.server.db.Scannersettings[id=" + id + "]";
  }

}
