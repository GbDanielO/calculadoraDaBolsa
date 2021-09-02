package br.com.calculadoradabolsa.utils;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@RevisionEntity
public class EnversCustomRevision {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @RevisionNumber
  private Long rev;

  @RevisionTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  private Long usuCodUsuario;

  public Long getRev() {
    return this.rev;
  }

  public void setRev( final Long rev ) {
    this.rev = rev;
  }

  public Date getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp( final Date timestamp ) {
    this.timestamp = timestamp;
  }

  public Long getUsuCodUsuario() {
    return this.usuCodUsuario;
  }

  public void setUsuCodUsuario( final Long usuCodUsuario ) {
    this.usuCodUsuario = usuCodUsuario;
  }

}
