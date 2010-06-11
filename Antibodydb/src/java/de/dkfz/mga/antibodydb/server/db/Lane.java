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
@Table(name = "DEV_T_Lane", catalog = "phpwebdb", schema = "dbo")
@NamedQueries({
  @NamedQuery(name = "Lane.findAll", query = "SELECT l FROM Lane l"),
  @NamedQuery(name = "Lane.findById", query = "SELECT l FROM Lane l WHERE l.id = :id"),
  @NamedQuery(name = "Lane.findByLysateProtein1", query = "SELECT l FROM Lane l WHERE l.lysateProtein1 = :lysateProtein1"),
  @NamedQuery(name = "Lane.findByLysateProtein2", query = "SELECT l FROM Lane l WHERE l.lysateProtein2 = :lysateProtein2"),
  @NamedQuery(name = "Lane.findByLysateProtein3", query = "SELECT l FROM Lane l WHERE l.lysateProtein3 = :lysateProtein3"),
  @NamedQuery(name = "Lane.findByLysateProtein4", query = "SELECT l FROM Lane l WHERE l.lysateProtein4 = :lysateProtein4"),
  @NamedQuery(name = "Lane.findByLysateProtein5", query = "SELECT l FROM Lane l WHERE l.lysateProtein5 = :lysateProtein5"),
  @NamedQuery(name = "Lane.findByLysateProtein6", query = "SELECT l FROM Lane l WHERE l.lysateProtein6 = :lysateProtein6"),
  @NamedQuery(name = "Lane.findByLysateProtein7", query = "SELECT l FROM Lane l WHERE l.lysateProtein7 = :lysateProtein7"),
  @NamedQuery(name = "Lane.findByLysateProtein8", query = "SELECT l FROM Lane l WHERE l.lysateProtein8 = :lysateProtein8"),
  @NamedQuery(name = "Lane.findByLysateProtein9", query = "SELECT l FROM Lane l WHERE l.lysateProtein9 = :lysateProtein9"),
  @NamedQuery(name = "Lane.findByLysateProtein10", query = "SELECT l FROM Lane l WHERE l.lysateProtein10 = :lysateProtein10"),
  @NamedQuery(name = "Lane.findByLysateProtein11", query = "SELECT l FROM Lane l WHERE l.lysateProtein11 = :lysateProtein11"),
  @NamedQuery(name = "Lane.findByLysateProtein12", query = "SELECT l FROM Lane l WHERE l.lysateProtein12 = :lysateProtein12"),
  @NamedQuery(name = "Lane.findByLysateProtein13", query = "SELECT l FROM Lane l WHERE l.lysateProtein13 = :lysateProtein13"),
  @NamedQuery(name = "Lane.findByLysateProtein14", query = "SELECT l FROM Lane l WHERE l.lysateProtein14 = :lysateProtein14"),
  @NamedQuery(name = "Lane.findByLysateProtein15", query = "SELECT l FROM Lane l WHERE l.lysateProtein15 = :lysateProtein15"),
  @NamedQuery(name = "Lane.findByTotalProtein1", query = "SELECT l FROM Lane l WHERE l.totalProtein1 = :totalProtein1"),
  @NamedQuery(name = "Lane.findByTotalProtein2", query = "SELECT l FROM Lane l WHERE l.totalProtein2 = :totalProtein2"),
  @NamedQuery(name = "Lane.findByTotalProtein3", query = "SELECT l FROM Lane l WHERE l.totalProtein3 = :totalProtein3"),
  @NamedQuery(name = "Lane.findByTotalProtein4", query = "SELECT l FROM Lane l WHERE l.totalProtein4 = :totalProtein4"),
  @NamedQuery(name = "Lane.findByTotalProtein5", query = "SELECT l FROM Lane l WHERE l.totalProtein5 = :totalProtein5"),
  @NamedQuery(name = "Lane.findByTotalProtein6", query = "SELECT l FROM Lane l WHERE l.totalProtein6 = :totalProtein6"),
  @NamedQuery(name = "Lane.findByTotalProtein7", query = "SELECT l FROM Lane l WHERE l.totalProtein7 = :totalProtein7"),
  @NamedQuery(name = "Lane.findByTotalProtein8", query = "SELECT l FROM Lane l WHERE l.totalProtein8 = :totalProtein8"),
  @NamedQuery(name = "Lane.findByTotalProtein9", query = "SELECT l FROM Lane l WHERE l.totalProtein9 = :totalProtein9"),
  @NamedQuery(name = "Lane.findByTotalProtein10", query = "SELECT l FROM Lane l WHERE l.totalProtein10 = :totalProtein10"),
  @NamedQuery(name = "Lane.findByTotalProtein11", query = "SELECT l FROM Lane l WHERE l.totalProtein11 = :totalProtein11"),
  @NamedQuery(name = "Lane.findByTotalProtein12", query = "SELECT l FROM Lane l WHERE l.totalProtein12 = :totalProtein12"),
  @NamedQuery(name = "Lane.findByTotalProtein13", query = "SELECT l FROM Lane l WHERE l.totalProtein13 = :totalProtein13"),
  @NamedQuery(name = "Lane.findByTotalProtein14", query = "SELECT l FROM Lane l WHERE l.totalProtein14 = :totalProtein14"),
  @NamedQuery(name = "Lane.findByTotalProtein15", query = "SELECT l FROM Lane l WHERE l.totalProtein15 = :totalProtein15")})
public class Lane implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Column(name = "Lysate_Protein_1")
  private String lysateProtein1;
  @Column(name = "Lysate_Protein_2")
  private String lysateProtein2;
  @Column(name = "Lysate_Protein_3")
  private String lysateProtein3;
  @Column(name = "Lysate_Protein_4")
  private String lysateProtein4;
  @Column(name = "Lysate_Protein_5")
  private String lysateProtein5;
  @Column(name = "Lysate_Protein_6")
  private String lysateProtein6;
  @Column(name = "Lysate_Protein_7")
  private String lysateProtein7;
  @Column(name = "Lysate_Protein_8")
  private String lysateProtein8;
  @Column(name = "Lysate_Protein_9")
  private String lysateProtein9;
  @Column(name = "Lysate_Protein_10")
  private String lysateProtein10;
  @Column(name = "Lysate_Protein_11")
  private String lysateProtein11;
  @Column(name = "Lysate_Protein_12")
  private String lysateProtein12;
  @Column(name = "Lysate_Protein_13")
  private String lysateProtein13;
  @Column(name = "Lysate_Protein_14")
  private String lysateProtein14;
  @Column(name = "Lysate_Protein_15")
  private String lysateProtein15;
  @Column(name = "Total_Protein_1")
  private String totalProtein1;
  @Column(name = "Total_Protein_2")
  private String totalProtein2;
  @Column(name = "Total_Protein_3")
  private String totalProtein3;
  @Column(name = "Total_Protein_4")
  private String totalProtein4;
  @Column(name = "Total_Protein_5")
  private String totalProtein5;
  @Column(name = "Total_Protein_6")
  private String totalProtein6;
  @Column(name = "Total_Protein_7")
  private String totalProtein7;
  @Column(name = "Total_Protein_8")
  private String totalProtein8;
  @Column(name = "Total_Protein_9")
  private String totalProtein9;
  @Column(name = "Total_Protein_10")
  private String totalProtein10;
  @Column(name = "Total_Protein_11")
  private String totalProtein11;
  @Column(name = "Total_Protein_12")
  private String totalProtein12;
  @Column(name = "Total_Protein_13")
  private String totalProtein13;
  @Column(name = "Total_Protein_14")
  private String totalProtein14;
  @Column(name = "Total_Protein_15")
  private String totalProtein15;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "fsTLaneid")
  private List<Images> imagesList;

  public Lane() {
  }

  public Lane(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLysateProtein1() {
    return lysateProtein1;
  }

  public void setLysateProtein1(String lysateProtein1) {
    this.lysateProtein1 = lysateProtein1;
  }

  public String getLysateProtein2() {
    return lysateProtein2;
  }

  public void setLysateProtein2(String lysateProtein2) {
    this.lysateProtein2 = lysateProtein2;
  }

  public String getLysateProtein3() {
    return lysateProtein3;
  }

  public void setLysateProtein3(String lysateProtein3) {
    this.lysateProtein3 = lysateProtein3;
  }

  public String getLysateProtein4() {
    return lysateProtein4;
  }

  public void setLysateProtein4(String lysateProtein4) {
    this.lysateProtein4 = lysateProtein4;
  }

  public String getLysateProtein5() {
    return lysateProtein5;
  }

  public void setLysateProtein5(String lysateProtein5) {
    this.lysateProtein5 = lysateProtein5;
  }

  public String getLysateProtein6() {
    return lysateProtein6;
  }

  public void setLysateProtein6(String lysateProtein6) {
    this.lysateProtein6 = lysateProtein6;
  }

  public String getLysateProtein7() {
    return lysateProtein7;
  }

  public void setLysateProtein7(String lysateProtein7) {
    this.lysateProtein7 = lysateProtein7;
  }

  public String getLysateProtein8() {
    return lysateProtein8;
  }

  public void setLysateProtein8(String lysateProtein8) {
    this.lysateProtein8 = lysateProtein8;
  }

  public String getLysateProtein9() {
    return lysateProtein9;
  }

  public void setLysateProtein9(String lysateProtein9) {
    this.lysateProtein9 = lysateProtein9;
  }

  public String getLysateProtein10() {
    return lysateProtein10;
  }

  public void setLysateProtein10(String lysateProtein10) {
    this.lysateProtein10 = lysateProtein10;
  }

  public String getLysateProtein11() {
    return lysateProtein11;
  }

  public void setLysateProtein11(String lysateProtein11) {
    this.lysateProtein11 = lysateProtein11;
  }

  public String getLysateProtein12() {
    return lysateProtein12;
  }

  public void setLysateProtein12(String lysateProtein12) {
    this.lysateProtein12 = lysateProtein12;
  }

  public String getLysateProtein13() {
    return lysateProtein13;
  }

  public void setLysateProtein13(String lysateProtein13) {
    this.lysateProtein13 = lysateProtein13;
  }

  public String getLysateProtein14() {
    return lysateProtein14;
  }

  public void setLysateProtein14(String lysateProtein14) {
    this.lysateProtein14 = lysateProtein14;
  }

  public String getLysateProtein15() {
    return lysateProtein15;
  }

  public void setLysateProtein15(String lysateProtein15) {
    this.lysateProtein15 = lysateProtein15;
  }

  public String getTotalProtein1() {
    return totalProtein1;
  }

  public void setTotalProtein1(String totalProtein1) {
    this.totalProtein1 = totalProtein1;
  }

  public String getTotalProtein2() {
    return totalProtein2;
  }

  public void setTotalProtein2(String totalProtein2) {
    this.totalProtein2 = totalProtein2;
  }

  public String getTotalProtein3() {
    return totalProtein3;
  }

  public void setTotalProtein3(String totalProtein3) {
    this.totalProtein3 = totalProtein3;
  }

  public String getTotalProtein4() {
    return totalProtein4;
  }

  public void setTotalProtein4(String totalProtein4) {
    this.totalProtein4 = totalProtein4;
  }

  public String getTotalProtein5() {
    return totalProtein5;
  }

  public void setTotalProtein5(String totalProtein5) {
    this.totalProtein5 = totalProtein5;
  }

  public String getTotalProtein6() {
    return totalProtein6;
  }

  public void setTotalProtein6(String totalProtein6) {
    this.totalProtein6 = totalProtein6;
  }

  public String getTotalProtein7() {
    return totalProtein7;
  }

  public void setTotalProtein7(String totalProtein7) {
    this.totalProtein7 = totalProtein7;
  }

  public String getTotalProtein8() {
    return totalProtein8;
  }

  public void setTotalProtein8(String totalProtein8) {
    this.totalProtein8 = totalProtein8;
  }

  public String getTotalProtein9() {
    return totalProtein9;
  }

  public void setTotalProtein9(String totalProtein9) {
    this.totalProtein9 = totalProtein9;
  }

  public String getTotalProtein10() {
    return totalProtein10;
  }

  public void setTotalProtein10(String totalProtein10) {
    this.totalProtein10 = totalProtein10;
  }

  public String getTotalProtein11() {
    return totalProtein11;
  }

  public void setTotalProtein11(String totalProtein11) {
    this.totalProtein11 = totalProtein11;
  }

  public String getTotalProtein12() {
    return totalProtein12;
  }

  public void setTotalProtein12(String totalProtein12) {
    this.totalProtein12 = totalProtein12;
  }

  public String getTotalProtein13() {
    return totalProtein13;
  }

  public void setTotalProtein13(String totalProtein13) {
    this.totalProtein13 = totalProtein13;
  }

  public String getTotalProtein14() {
    return totalProtein14;
  }

  public void setTotalProtein14(String totalProtein14) {
    this.totalProtein14 = totalProtein14;
  }

  public String getTotalProtein15() {
    return totalProtein15;
  }

  public void setTotalProtein15(String totalProtein15) {
    this.totalProtein15 = totalProtein15;
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
    if (!(object instanceof Lane)) {
      return false;
    }
    Lane other = (Lane) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "de.dkfz.mga.antibodydb.server.db.Lane[id=" + id + "]";
  }

}
