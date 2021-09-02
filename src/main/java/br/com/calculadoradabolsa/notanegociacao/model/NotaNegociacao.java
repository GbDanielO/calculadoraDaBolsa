package br.com.calculadoradabolsa.notanegociacao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.calculadoradabolsa.utils.EntidadePadrao;

/**
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 23-novembro-2019 07:45
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
@Entity
@Table(name = "t_nota_negociacao")
public class NotaNegociacao extends EntidadePadrao implements Comparable<NotaNegociacao> {

  /**
   * 
   */
  private static final long serialVersionUID = -8565236068279002307L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_nota_negociacao")
  private Long id;

  @Column(name = "numero_nota")
  private Long numeroNota;

  @Temporal(TemporalType.DATE)
  @Column(name = "data_pregao")
  private Date dataPregao;

  private Long competencia;

  private String cpf;

  @OneToMany(mappedBy = "notaNegociacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<NegocioRealizado> lstNegocioRealizado;

  @OneToMany(mappedBy = "notaNegociacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<ResumoFinanceiro> lstResumoFinanceiro;

  @OneToMany(mappedBy = "notaNegociacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<ResumoNegocios> lstResumoNegocios;

  public NotaNegociacao() {
  }

  public NotaNegociacao( Long numeroNota, Date dataPregao, String cpf, Long competencia ) {
    super();
    this.numeroNota = numeroNota;
    this.dataPregao = dataPregao;
    this.cpf = cpf;
    this.competencia = competencia;
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public void setId( Long id ) {
    this.id = id;
  }

  public Long getNumeroNota() {
    return this.numeroNota;
  }

  public void setNumeroNota( Long numeroNota ) {
    this.numeroNota = numeroNota;
  }

  public Date getDataPregao() {
    return this.dataPregao;
  }

  public void setDataPregao( Date dataPregao ) {
    this.dataPregao = dataPregao;
  }

  public String getCpf() {
    return this.cpf;
  }

  public void setCpf( String cpf ) {
    this.cpf = cpf;
  }

  public List<NegocioRealizado> getLstNegocioRealizado() {
    return this.lstNegocioRealizado;
  }

  public void setLstNegocioRealizado( List<NegocioRealizado> lstNegocioRealizado ) {
    this.lstNegocioRealizado = lstNegocioRealizado;
  }

  public List<ResumoFinanceiro> getLstResumoFinanceiro() {
    return this.lstResumoFinanceiro;
  }

  public void setLstResumoFinanceiro( List<ResumoFinanceiro> lstResumoFinanceiro ) {
    this.lstResumoFinanceiro = lstResumoFinanceiro;
  }

  public List<ResumoNegocios> getLstResumoNegocios() {
    return this.lstResumoNegocios;
  }

  public void setLstResumoNegocios( List<ResumoNegocios> lstResumoNegocios ) {
    this.lstResumoNegocios = lstResumoNegocios;
  }

  public Long getCompetencia() {
    return this.competencia;
  }

  public void setCompetencia( Long competencia ) {
    this.competencia = competencia;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ( ( this.numeroNota == null ) ? 0 : this.numeroNota.hashCode() );
    return result;
  }

  @Override
  public boolean equals( Object obj ) {
    if ( this == obj )
      return true;
    if ( !super.equals( obj ) )
      return false;
    if ( getClass() != obj.getClass() )
      return false;
    NotaNegociacao other = (NotaNegociacao) obj;
    if ( this.numeroNota == null ) {
      if ( other.numeroNota != null )
        return false;
    } else if ( !this.numeroNota.equals( other.numeroNota ) )
      return false;
    return true;
  }

  /**
   * For dataPregao:
   * 
   * the value 0 if the argument Date is equal to this Date; a value less than 0 if this Date is before the Date
   * argument; and a value greater than 0 if this Date is after the Date argument.
   */
  @Override
  public int compareTo( NotaNegociacao o ) {

    return this.dataPregao.compareTo( o.dataPregao );
  }

}
