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
@Table(name = "DEV_T_SDS", catalog = "phpwebdb", schema = "dbo")
@NamedQueries({
  @NamedQuery(name = "Sds.findAll", query = "SELECT s FROM Sds s"),
  @NamedQuery(name = "Sds.findById", query = "SELECT s FROM Sds s WHERE s.id = :id"),
  @NamedQuery(name = "Sds.findBySds", query = "SELECT s FROM Sds s WHERE s.sds = :sds"),
  @NamedQuery(name = "Sds.findByAcrylamid", query = "SELECT s FROM Sds s WHERE s.acrylamid = :acrylamid"),
  @NamedQuery(name = "Sds.findBySep", query = "SELECT s FROM Sds s WHERE s.sep = :sep"),
  @NamedQuery(name = "Sds.findByVoltage", query = "SELECT s FROM Sds s WHERE s.voltage = :voltage"),
  @NamedQuery(name = "Sds.findBySize", query = "SELECT s FROM Sds s WHERE s.size = :size")})
public class Sds implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Column(name = "SDS")
  private String sds;
  @Column(name = "Acrylamid")
  private String acrylamid;
  @Column(name = "Sep")
  private String sep;
  @Column(name = "Voltage")
  private String voltage;
  @Column(name = "Size")
  private String size;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "fsTSDSid")
  private List<Images> imagesList;

  public Sds() {
  }

  public Sds(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSds() {
    return sds;
  }

  public void setSds(String sds) {
    this.sds = sds;
  }

  public String getAcrylamid() {
    return acrylamid;
  }

  public void setAcrylamid(String acrylamid) {
    this.acrylamid = acrylamid;
  }

  public String getSep() {
    return sep;
  }

  public void setSep(String sep) {
    this.sep = sep;
  }

  public String getVoltage() {
    return voltage;
  }

  public void setVoltage(String voltage) {
    this.voltage = voltage;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
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
    if (!(object instanceof Sds)) {
      return false;
    }
    Sds other = (Sds) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "de.dkfz.mga.antibodydb.server.db.Sds[id=" + id + "]";
  }

}
