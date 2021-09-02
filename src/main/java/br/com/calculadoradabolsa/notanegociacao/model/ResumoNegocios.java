package br.com.calculadoradabolsa.notanegociacao.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.calculadoradabolsa.notanegociacao.enums.EnumResumoDosNegocios;
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
@Table(name = "t_resumo_negocios")
public class ResumoNegocios extends EntidadePadrao {

  /**
   * 
   */
  private static final long serialVersionUID = -8421122751942621782L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_resumo_negocios")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_nota_negociacao")
  private NotaNegociacao notaNegociacao;

  @Enumerated(EnumType.STRING)
  @Column(name = "descricao")
  private EnumResumoDosNegocios enumResumoDosNegocios;

  private BigDecimal valor;

  public ResumoNegocios() {
  };

  public ResumoNegocios( NotaNegociacao notaNegociacao, EnumResumoDosNegocios enumResumoDosNegocios,
      BigDecimal valor ) {
    super();
    this.notaNegociacao = notaNegociacao;
    this.enumResumoDosNegocios = enumResumoDosNegocios;
    this.valor = valor;
  }

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public void setId( Long id ) {
    this.id = id;
  }

  public NotaNegociacao getNotaNegociacao() {
    return this.notaNegociacao;
  }

  public void setNotaNegociacao( NotaNegociacao notaNegociacao ) {
    this.notaNegociacao = notaNegociacao;
  }

  public EnumResumoDosNegocios getEnumResumoDosNegocios() {
    return this.enumResumoDosNegocios;
  }

  public void setEnumResumoDosNegocios( EnumResumoDosNegocios enumResumoDosNegocios ) {
    this.enumResumoDosNegocios = enumResumoDosNegocios;
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  public void setValor( BigDecimal valor ) {
    this.valor = valor;
  }

}
