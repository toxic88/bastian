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
@Table(name = "DEV_T_Antibody", catalog = "phpwebdb", schema = "dbo")
@NamedQueries({
  @NamedQuery(name = "Antibody.findAll", query = "SELECT a FROM Antibody a"),
  @NamedQuery(name = "Antibody.findById", query = "SELECT a FROM Antibody a WHERE a.id = :id"),
  @NamedQuery(name = "Antibody.findByAntibody", query = "SELECT a FROM Antibody a WHERE a.antibody = :antibody"),
  @NamedQuery(name = "Antibody.findByLot", query = "SELECT a FROM Antibody a WHERE a.lot = :lot"),
  @NamedQuery(name = "Antibody.findBySource", query = "SELECT a FROM Antibody a WHERE a.source = :source"),
  @NamedQuery(name = "Antibody.findByDilutionWestern", query = "SELECT a FROM Antibody a WHERE a.dilutionWestern = :dilutionWestern"),
  @NamedQuery(name = "Antibody.findByStock", query = "SELECT a FROM Antibody a WHERE a.stock = :stock"),
  @NamedQuery(name = "Antibody.findByEmbedding", query = "SELECT a FROM Antibody a WHERE a.embedding = :embedding"),
  @NamedQuery(name = "Antibody.findByFixation", query = "SELECT a FROM Antibody a WHERE a.fixation = :fixation"),
  @NamedQuery(name = "Antibody.findByDatasheetpath", query = "SELECT a FROM Antibody a WHERE a.datasheetpath = :datasheetpath"),
  @NamedQuery(name = "Antibody.findByWestern", query = "SELECT a FROM Antibody a WHERE a.western = :western"),
  @NamedQuery(name = "Antibody.findByCaptureArray", query = "SELECT a FROM Antibody a WHERE a.captureArray = :captureArray"),
  @NamedQuery(name = "Antibody.findByRppa", query = "SELECT a FROM Antibody a WHERE a.rppa = :rppa"),
  @NamedQuery(name = "Antibody.findByIp", query = "SELECT a FROM Antibody a WHERE a.ip = :ip"),
  @NamedQuery(name = "Antibody.findByIf1", query = "SELECT a FROM Antibody a WHERE a.if1 = :if1"),
  @NamedQuery(name = "Antibody.findByIhc", query = "SELECT a FROM Antibody a WHERE a.ihc = :ihc"),
  @NamedQuery(name = "Antibody.findByFacs", query = "SELECT a FROM Antibody a WHERE a.facs = :facs"),
  @NamedQuery(name = "Antibody.findByEvaluationWestern", query = "SELECT a FROM Antibody a WHERE a.evaluationWestern = :evaluationWestern"),
  @NamedQuery(name = "Antibody.findByEvaluationCaptureArray", query = "SELECT a FROM Antibody a WHERE a.evaluationCaptureArray = :evaluationCaptureArray"),
  @NamedQuery(name = "Antibody.findByEvaluationRPPA", query = "SELECT a FROM Antibody a WHERE a.evaluationRPPA = :evaluationRPPA"),
  @NamedQuery(name = "Antibody.findByEvaluationIP", query = "SELECT a FROM Antibody a WHERE a.evaluationIP = :evaluationIP"),
  @NamedQuery(name = "Antibody.findByEvaluationIF", query = "SELECT a FROM Antibody a WHERE a.evaluationIF = :evaluationIF"),
  @NamedQuery(name = "Antibody.findByEvaluationIHC", query = "SELECT a FROM Antibody a WHERE a.evaluationIHC = :evaluationIHC"),
  @NamedQuery(name = "Antibody.findByEvaluationFACS", query = "SELECT a FROM Antibody a WHERE a.evaluationFACS = :evaluationFACS"),
  @NamedQuery(name = "Antibody.findByValidated", query = "SELECT a FROM Antibody a WHERE a.validated = :validated")})
public class Antibody implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @Column(name = "Antibody")
  private String antibody;
  @Column(name = "Lot")
  private String lot;
  @Column(name = "Source")
  private String source;
  @Column(name = "Dilution_Western")
  private String dilutionWestern;
  @Column(name = "Stock")
  private String stock;
  @Column(name = "Embedding")
  private String embedding;
  @Column(name = "Fixation")
  private String fixation;
  @Column(name = "Datasheetpath")
  private String datasheetpath;
  @Column(name = "Western")
  private String western;
  @Column(name = "Capture_Array")
  private String captureArray;
  @Column(name = "RPPA")
  private String rppa;
  @Column(name = "IP")
  private String ip;
  @Column(name = "IF")
  private String if1;
  @Column(name = "IHC")
  private String ihc;
  @Column(name = "FACS")
  private String facs;
  @Column(name = "Evaluation_Western")
  private Integer evaluationWestern;
  @Column(name = "Evaluation_Capture_Array")
  private Integer evaluationCaptureArray;
  @Column(name = "Evaluation_RPPA")
  private Integer evaluationRPPA;
  @Column(name = "Evaluation_IP")
  private Integer evaluationIP;
  @Column(name = "Evaluation_IF")
  private Integer evaluationIF;
  @Column(name = "Evaluation_IHC")
  private Integer evaluationIHC;
  @Column(name = "Evaluation_FACS")
  private Integer evaluationFACS;
  @Basic(optional = false)
  @Column(name = "Validated")
  private int validated;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "antibody")
  private List<Images> imagesList;
  @JoinColumn(name = "fs_T_Targetprotein_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Targetprotein targetprotein;
  @JoinColumn(name = "fs_T_Incubationprotocol_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Incubationprotocol incubationprotocol;

  public Antibody() {
  }

  public Antibody(Integer id) {
    this.id = id;
  }

  public Antibody(Integer id, String antibody, int validated) {
    this.id = id;
    this.antibody = antibody;
    this.validated = validated;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAntibody() {
    return antibody;
  }

  public void setAntibody(String antibody) {
    this.antibody = antibody;
  }

  public String getLot() {
    return lot;
  }

  public void setLot(String lot) {
    this.lot = lot;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDilutionWestern() {
    return dilutionWestern;
  }

  public void setDilutionWestern(String dilutionWestern) {
    this.dilutionWestern = dilutionWestern;
  }

  public String getStock() {
    return stock;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public String getEmbedding() {
    return embedding;
  }

  public void setEmbedding(String embedding) {
    this.embedding = embedding;
  }

  public String getFixation() {
    return fixation;
  }

  public void setFixation(String fixation) {
    this.fixation = fixation;
  }

  public String getDatasheetpath() {
    return datasheetpath;
  }

  public void setDatasheetpath(String datasheetpath) {
    this.datasheetpath = datasheetpath;
  }

  public String getWestern() {
    return western;
  }

  public void setWestern(String western) {
    this.western = western;
  }

  public String getCaptureArray() {
    return captureArray;
  }

  public void setCaptureArray(String captureArray) {
    this.captureArray = captureArray;
  }

  public String getRppa() {
    return rppa;
  }

  public void setRppa(String rppa) {
    this.rppa = rppa;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getIf1() {
    return if1;
  }

  public void setIf1(String if1) {
    this.if1 = if1;
  }

  public String getIhc() {
    return ihc;
  }

  public void setIhc(String ihc) {
    this.ihc = ihc;
  }

  public String getFacs() {
    return facs;
  }

  public void setFacs(String facs) {
    this.facs = facs;
  }

  public Integer getEvaluationWestern() {
    return evaluationWestern;
  }

  public void setEvaluationWestern(Integer evaluationWestern) {
    this.evaluationWestern = evaluationWestern;
  }

  public Integer getEvaluationCaptureArray() {
    return evaluationCaptureArray;
  }

  public void setEvaluationCaptureArray(Integer evaluationCaptureArray) {
    this.evaluationCaptureArray = evaluationCaptureArray;
  }

  public Integer getEvaluationRPPA() {
    return evaluationRPPA;
  }

  public void setEvaluationRPPA(Integer evaluationRPPA) {
    this.evaluationRPPA = evaluationRPPA;
  }

  public Integer getEvaluationIP() {
    return evaluationIP;
  }

  public void setEvaluationIP(Integer evaluationIP) {
    this.evaluationIP = evaluationIP;
  }

  public Integer getEvaluationIF() {
    return evaluationIF;
  }

  public void setEvaluationIF(Integer evaluationIF) {
    this.evaluationIF = evaluationIF;
  }

  public Integer getEvaluationIHC() {
    return evaluationIHC;
  }

  public void setEvaluationIHC(Integer evaluationIHC) {
    this.evaluationIHC = evaluationIHC;
  }

  public Integer getEvaluationFACS() {
    return evaluationFACS;
  }

  public void setEvaluationFACS(Integer evaluationFACS) {
    this.evaluationFACS = evaluationFACS;
  }

  public int getValidated() {
    return validated;
  }

  public void setValidated(int validated) {
    this.validated = validated;
  }

  public List<Images> getImagesList() {
    return imagesList;
  }

  public void setImagesList(List<Images> imagesList) {
    this.imagesList = imagesList;
  }

  public Targetprotein getTargetprotein() {
    return targetprotein;
  }

  public void setTargetprotein(Targetprotein targetprotein) {
    this.targetprotein = targetprotein;
  }

  public Incubationprotocol getIncubationprotocol() {
    return incubationprotocol;
  }

  public void setIncubationprotocol(Incubationprotocol incubationprotocol) {
    this.incubationprotocol = incubationprotocol;
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
    if (!(object instanceof Antibody)) {
      return false;
    }
    Antibody other = (Antibody) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "de.dkfz.mga.antibodydb.server.db.Antibody[id=" + id + "]";
  }

}
