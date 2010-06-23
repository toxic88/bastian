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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author bastian
 */
@Entity
@Table(name = "DEV_T_Incubationprotocol", catalog = "phpwebdb", schema = "dbo")
@NamedQueries({
  @NamedQuery(name = "Incubationprotocol.findAll", query = "SELECT i FROM Incubationprotocol i"),
  @NamedQuery(name = "Incubationprotocol.findById", query = "SELECT i FROM Incubationprotocol i WHERE i.id = :id"),
  @NamedQuery(name = "Incubationprotocol.findByIncubationprotocol", query = "SELECT i FROM Incubationprotocol i WHERE i.incubationprotocol = :incubationprotocol"),
  @NamedQuery(name = "Incubationprotocol.findByBlocking", query = "SELECT i FROM Incubationprotocol i WHERE i.blocking = :blocking"),
  @NamedQuery(name = "Incubationprotocol.findByIncubation1", query = "SELECT i FROM Incubationprotocol i WHERE i.incubation1 = :incubation1"),
  @NamedQuery(name = "Incubationprotocol.findByIncubation2", query = "SELECT i FROM Incubationprotocol i WHERE i.incubation2 = :incubation2"),
  @NamedQuery(name = "Incubationprotocol.findByWashing1", query = "SELECT i FROM Incubationprotocol i WHERE i.washing1 = :washing1"),
  @NamedQuery(name = "Incubationprotocol.findByWashing2", query = "SELECT i FROM Incubationprotocol i WHERE i.washing2 = :washing2")})
public class Incubationprotocol implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Column(name = "Incubationprotocol")
  private String incubationprotocol;
  @Column(name = "Blocking")
  private String blocking;
  @Column(name = "Incubation_1")
  private String incubation1;
  @Column(name = "Incubation_2")
  private String incubation2;
  @Column(name = "Washing_1")
  private String washing1;
  @Column(name = "Washing_2")
  private String washing2;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "incubationprotocol")
  private List<Antibody> antibodyList;
  @JoinColumn(name = "fs_T_Bufferset_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Bufferset bufferset;

  public Incubationprotocol() {
  }

  public Incubationprotocol(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getIncubationprotocol() {
    return incubationprotocol;
  }

  public void setIncubationprotocol(String incubationprotocol) {
    this.incubationprotocol = incubationprotocol;
  }

  public String getBlocking() {
    return blocking;
  }

  public void setBlocking(String blocking) {
    this.blocking = blocking;
  }

  public String getIncubation1() {
    return incubation1;
  }

  public void setIncubation1(String incubation1) {
    this.incubation1 = incubation1;
  }

  public String getIncubation2() {
    return incubation2;
  }

  public void setIncubation2(String incubation2) {
    this.incubation2 = incubation2;
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

  public List<Antibody> getAntibodyList() {
    return antibodyList;
  }

  public void setAntibodyList(List<Antibody> antibodyList) {
    this.antibodyList = antibodyList;
  }

  public Bufferset getBufferset() {
    return bufferset;
  }

  public void setBufferset(Bufferset bufferset) {
    this.bufferset = bufferset;
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
    if (!(object instanceof Incubationprotocol)) {
      return false;
    }
    Incubationprotocol other = (Incubationprotocol) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "de.dkfz.mga.antibodydb.server.db.Incubationprotocol[id=" + id + "]";
  }

}
