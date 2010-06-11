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
@Table(name = "DEV_T_Targetprotein", catalog = "phpwebdb", schema = "dbo")
@NamedQueries({
  @NamedQuery(name = "Targetprotein.findAll", query = "SELECT t FROM Targetprotein t"),
  @NamedQuery(name = "Targetprotein.findById", query = "SELECT t FROM Targetprotein t WHERE t.id = :id"),
  @NamedQuery(name = "Targetprotein.findByTargetprotein", query = "SELECT t FROM Targetprotein t WHERE t.targetprotein = :targetprotein"),
  @NamedQuery(name = "Targetprotein.findByMWkD", query = "SELECT t FROM Targetprotein t WHERE t.mWkD = :mWkD"),
  @NamedQuery(name = "Targetprotein.findBySwissProtAccsession", query = "SELECT t FROM Targetprotein t WHERE t.swissProtAccsession = :swissProtAccsession"),
  @NamedQuery(name = "Targetprotein.findBySupplier", query = "SELECT t FROM Targetprotein t WHERE t.supplier = :supplier"),
  @NamedQuery(name = "Targetprotein.findByStock", query = "SELECT t FROM Targetprotein t WHERE t.stock = :stock"),
  @NamedQuery(name = "Targetprotein.findByReferences", query = "SELECT t FROM Targetprotein t WHERE t.references = :references")})
public class Targetprotein implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Column(name = "Targetprotein")
  private String targetprotein;
  @Column(name = "MW_kD")
  private String mWkD;
  @Column(name = "SwissProt_Accsession")
  private String swissProtAccsession;
  @Column(name = "Supplier")
  private String supplier;
  @Column(name = "Stock")
  private String stock;
  @Column(name = "References")
  private String references;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "fsTTargetproteinid")
  private List<Antibody> antibodyList;

  public Targetprotein() {
  }

  public Targetprotein(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTargetprotein() {
    return targetprotein;
  }

  public void setTargetprotein(String targetprotein) {
    this.targetprotein = targetprotein;
  }

  public String getMWkD() {
    return mWkD;
  }

  public void setMWkD(String mWkD) {
    this.mWkD = mWkD;
  }

  public String getSwissProtAccsession() {
    return swissProtAccsession;
  }

  public void setSwissProtAccsession(String swissProtAccsession) {
    this.swissProtAccsession = swissProtAccsession;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getStock() {
    return stock;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public String getReferences() {
    return references;
  }

  public void setReferences(String references) {
    this.references = references;
  }

  public List<Antibody> getAntibodyList() {
    return antibodyList;
  }

  public void setAntibodyList(List<Antibody> antibodyList) {
    this.antibodyList = antibodyList;
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
    if (!(object instanceof Targetprotein)) {
      return false;
    }
    Targetprotein other = (Targetprotein) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "de.dkfz.mga.antibodydb.server.db.Targetprotein[id=" + id + "]";
  }

}
