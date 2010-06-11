package de.dkfz.mga.antibodydb.shared;

import java.io.Serializable;
import java.util.Set;

public class Antibody implements Serializable {

  private long id;

  private String antibody;

  private String lot;

  private String source;

  private long targetprotein;

  private String dilutionWestern;

  private long incubationProtocol;

  private String stock;

  private String evaluationWestern;

  private String evaluationCaptureArray;

  private String evaluationRppa;

  private String commentWestern;

  private String commentCaptureArray;

  private String commentRppa;

  private String ip;

  private String _if;

  private String ihc;

  private String facts;

  private String commentIp;

  private String commentIf;

  private String commentIhc;

  private String commentFacts;

  private String embedding;

  private String fixation;

  private String validatedBy;

  private String datasheet;

  private Set<Long> westernImages;

  /* Getter / Setter */

  public Antibody() {
    
  }

  public String getAntibody() {
    return antibody;
  }

  public void setAntibody(String antibody) {
    this.antibody = antibody;
  }

  public String getIf() {
    return _if;
  }

  public void setIf(String _if) {
    this._if = _if;
  }

  public String getCommentCaptureArray() {
    return commentCaptureArray;
  }

  public void setCommentCaptureArray(String commentCaptureArray) {
    this.commentCaptureArray = commentCaptureArray;
  }

  public String getCommentFacts() {
    return commentFacts;
  }

  public void setCommentFacts(String commentFacts) {
    this.commentFacts = commentFacts;
  }

  public String getCommentIf() {
    return commentIf;
  }

  public void setCommentIf(String commentIf) {
    this.commentIf = commentIf;
  }

  public String getCommentIhc() {
    return commentIhc;
  }

  public void setCommentIhc(String commentIhc) {
    this.commentIhc = commentIhc;
  }

  public String getCommentIp() {
    return commentIp;
  }

  public void setCommentIp(String commentIp) {
    this.commentIp = commentIp;
  }

  public String getCommentRppa() {
    return commentRppa;
  }

  public void setCommentRppa(String commentRppa) {
    this.commentRppa = commentRppa;
  }

  public String getCommentWestern() {
    return commentWestern;
  }

  public void setCommentWestern(String commentWestern) {
    this.commentWestern = commentWestern;
  }

  public String getDatasheet() {
    return datasheet;
  }

  public void setDatasheet(String datasheet) {
    this.datasheet = datasheet;
  }

  public String getDilutionWestern() {
    return dilutionWestern;
  }

  public void setDilutionWestern(String dilutionWestern) {
    this.dilutionWestern = dilutionWestern;
  }

  public String getEmbedding() {
    return embedding;
  }

  public void setEmbedding(String embedding) {
    this.embedding = embedding;
  }

  public String getEvaluationCaptureArray() {
    return evaluationCaptureArray;
  }

  public void setEvaluationCaptureArray(String evaluationCaptureArray) {
    this.evaluationCaptureArray = evaluationCaptureArray;
  }

  public String getEvaluationRppa() {
    return evaluationRppa;
  }

  public void setEvaluationRppa(String evaluationRppa) {
    this.evaluationRppa = evaluationRppa;
  }

  public String getEvaluationWestern() {
    return evaluationWestern;
  }

  public void setEvaluationWestern(String evaluationWestern) {
    this.evaluationWestern = evaluationWestern;
  }

  public String getFacts() {
    return facts;
  }

  public void setFacts(String facts) {
    this.facts = facts;
  }

  public String getFixation() {
    return fixation;
  }

  public void setFixation(String fixation) {
    this.fixation = fixation;
  }

  public String getIhc() {
    return ihc;
  }

  public void setIhc(String ihc) {
    this.ihc = ihc;
  }

  public long getIncubationProtocol() {
    return incubationProtocol;
  }

  public void setIncubationProtocol(long incubationProtocol) {
    this.incubationProtocol = incubationProtocol;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
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

  public String getStock() {
    return stock;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public long getTargetprotein() {
    return targetprotein;
  }

  public void setTargetprotein(long targetprotein) {
    this.targetprotein = targetprotein;
  }

  public String getValidatedBy() {
    return validatedBy;
  }

  public void setValidatedBy(String validatedBy) {
    this.validatedBy = validatedBy;
  }

  public Set<Long> getWesternImages() {
    return westernImages;
  }

  public void setWesternImages(Set<Long> westernImages) {
    this.westernImages = westernImages;
  }

}
