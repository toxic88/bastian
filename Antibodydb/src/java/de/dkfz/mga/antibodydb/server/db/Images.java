/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dkfz.mga.antibodydb.server.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author bastian
 */
@Entity
@Table(name = "DEV_T_Images", catalog = "phpwebdb", schema = "dbo")
@NamedQueries({
  @NamedQuery(name = "Images.findAll", query = "SELECT i FROM Images i"),
  @NamedQuery(name = "Images.findById", query = "SELECT i FROM Images i WHERE i.id = :id"),
  @NamedQuery(name = "Images.findByImagepath", query = "SELECT i FROM Images i WHERE i.imagepath = :imagepath")})
public class Images implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "Imagepath")
  private String imagepath;
  @JoinColumn(name = "fs_T_Antibody_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Antibody fsTAntibodyid;
  @JoinColumn(name = "fs_T_Lane_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Lane fsTLaneid;
  @JoinColumn(name = "fs_T_Scannersettings_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Scannersettings fsTScannersettingsid;
  @JoinColumn(name = "fs_T_SDS_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Sds fsTSDSid;

  public Images() {
  }

  public Images(Integer id) {
    this.id = id;
  }

  public Images(Integer id, String imagepath) {
    this.id = id;
    this.imagepath = imagepath;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getImagepath() {
    return imagepath;
  }

  public void setImagepath(String imagepath) {
    this.imagepath = imagepath;
  }

  public Antibody getFsTAntibodyid() {
    return fsTAntibodyid;
  }

  public void setFsTAntibodyid(Antibody fsTAntibodyid) {
    this.fsTAntibodyid = fsTAntibodyid;
  }

  public Lane getFsTLaneid() {
    return fsTLaneid;
  }

  public void setFsTLaneid(Lane fsTLaneid) {
    this.fsTLaneid = fsTLaneid;
  }

  public Scannersettings getFsTScannersettingsid() {
    return fsTScannersettingsid;
  }

  public void setFsTScannersettingsid(Scannersettings fsTScannersettingsid) {
    this.fsTScannersettingsid = fsTScannersettingsid;
  }

  public Sds getFsTSDSid() {
    return fsTSDSid;
  }

  public void setFsTSDSid(Sds fsTSDSid) {
    this.fsTSDSid = fsTSDSid;
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
    if (!(object instanceof Images)) {
      return false;
    }
    Images other = (Images) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "de.dkfz.mga.antibodydb.server.db.Images[id=" + id + "]";
  }

}
