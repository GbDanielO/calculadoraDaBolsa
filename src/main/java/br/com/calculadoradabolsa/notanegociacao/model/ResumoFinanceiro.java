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

import br.com.calculadoradabolsa.notanegociacao.enums.EnumResumoFinanceiro;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoItem;
import br.com.calculadoradabolsa.utils.EntidadePadrao;

/**
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 23-novembro-2019 07:46
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
@Entity
@Table(name = "t_resumo_financeiro")
public class ResumoFinanceiro extends EntidadePadrao {

  /**
   * 
   */
  private static final long serialVersionUID = 8515751517780551557L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_resumo_financeiro")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_nota_negociacao")
  private NotaNegociacao notaNegociacao;

  @Enumerated(EnumType.STRING)
  @Column(name = "descricao")
  private EnumResumoFinanceiro enumResumoFinanceiro;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_item")
  private EnumTipoItem enumTipoItem;

  private BigDecimal valor;

  public ResumoFinanceiro() {
  }

  public ResumoFinanceiro( NotaNegociacao notaNegociacao, EnumResumoFinanceiro enumResumoFinanceiro,
      EnumTipoItem enumTipoItem, BigDecimal valor ) {
    super();
    this.notaNegociacao = notaNegociacao;
    this.enumResumoFinanceiro = enumResumoFinanceiro;
    this.enumTipoItem = enumTipoItem;
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

  public EnumResumoFinanceiro getEnumResumoFinanceiro() {
    return this.enumResumoFinanceiro;
  }

  public void setEnumResumoFinanceiro( EnumResumoFinanceiro enumResumoFinanceiro ) {
    this.enumResumoFinanceiro = enumResumoFinanceiro;
  }

  public EnumTipoItem getEnumTipoItem() {
    return this.enumTipoItem;
  }

  public void setEnumTipoItem( EnumTipoItem enumTipoItem ) {
    this.enumTipoItem = enumTipoItem;
  }

  public BigDecimal getValor() {
    return this.valor;
  }

  public void setValor( BigDecimal valor ) {
    this.valor = valor;
  }

}
