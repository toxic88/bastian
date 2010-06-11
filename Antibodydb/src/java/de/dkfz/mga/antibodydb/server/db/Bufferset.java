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
@Table(name = "DEV_T_Bufferset", catalog = "phpwebdb", schema = "dbo")
@NamedQueries({
  @NamedQuery(name = "Bufferset.findAll", query = "SELECT b FROM Bufferset b"),
  @NamedQuery(name = "Bufferset.findById", query = "SELECT b FROM Bufferset b WHERE b.id = :id"),
  @NamedQuery(name = "Bufferset.findByBufferset", query = "SELECT b FROM Bufferset b WHERE b.bufferset = :bufferset"),
  @NamedQuery(name = "Bufferset.findByWashbuffer", query = "SELECT b FROM Bufferset b WHERE b.washbuffer = :washbuffer"),
  @NamedQuery(name = "Bufferset.findByIncubation1", query = "SELECT b FROM Bufferset b WHERE b.incubation1 = :incubation1"),
  @NamedQuery(name = "Bufferset.findByIncubation2", query = "SELECT b FROM Bufferset b WHERE b.incubation2 = :incubation2"),
  @NamedQuery(name = "Bufferset.findByBlocking", query = "SELECT b FROM Bufferset b WHERE b.blocking = :blocking")})
public class Bufferset implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Column(name = "Bufferset")
  private String bufferset;
  @Column(name = "Washbuffer")
  private String washbuffer;
  @Column(name = "Incubation_1")
  private String incubation1;
  @Column(name = "Incubation_2")
  private String incubation2;
  @Column(name = "Blocking")
  private String blocking;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "fsTBuffersetid")
  private List<Incubationprotocol> incubationprotocolList;

  public Bufferset() {
  }

  public Bufferset(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getBufferset() {
    return bufferset;
  }

  public void setBufferset(String bufferset) {
    this.bufferset = bufferset;
  }

  public String getWashbuffer() {
    return washbuffer;
  }

  public void setWashbuffer(String washbuffer) {
    this.washbuffer = washbuffer;
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

  public String getBlocking() {
    return blocking;
  }

  public void setBlocking(String blocking) {
    this.blocking = blocking;
  }

  public List<Incubationprotocol> getIncubationprotocolList() {
    return incubationprotocolList;
  }

  public void setIncubationprotocolList(List<Incubationprotocol> incubationprotocolList) {
    this.incubationprotocolList = incubationprotocolList;
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
    if (!(object instanceof Bufferset)) {
      return false;
    }
    Bufferset other = (Bufferset) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "de.dkfz.mga.antibodydb.server.db.Bufferset[id=" + id + "]";
  }

}
