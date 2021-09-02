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

import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoAcao;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoItem;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoMercado;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoOperacao;
import br.com.calculadoradabolsa.utils.EntidadePadrao;

/**
 * 
 * @author Daniel Oliveira
 * 
 *         calculadoradabolsa: 23-novembro-2019 10:37
 *
 *         Todos os direitos reservados. Nenhuma parte de código desse projeto
 *         pode ser copiada sem autorização expressa para quaisquer fins.
 */
@Entity
@Table(name = "t_negocio_realizado")
public class NegocioRealizado extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3846164241697580997L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_negocio_realizado")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nota_negociacao")
	private NotaNegociacao notaNegociacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_operacao")
	private EnumTipoOperacao enumTipoOperacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_mercado")
	private EnumTipoMercado enumTipoMercado;

	@Column(name = "prazo")
	private String prazo;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_acao")
	private EnumTipoAcao enumTipoAcao;

	private String empresa;

	@Column(name = "day_trade")
	private Boolean dayTrade;

	private Integer quantidade;

	@Column(name = "preco_unitario")
	private BigDecimal precoUnitario;

	@Column(name = "preco_total")
	private BigDecimal precoTotal;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_item")
	private EnumTipoItem enumTipoItem;

	public NegocioRealizado() {
	}

	public NegocioRealizado(NotaNegociacao notaNegociacao, EnumTipoOperacao enumTipoOperacao,
			EnumTipoMercado enumTipoMercado, String prazo, EnumTipoAcao enumTipoAcao, String empresa, Boolean dayTrade,
			Integer quantidade, BigDecimal precoUnitario, BigDecimal precoTotal, EnumTipoItem enumTipoItem) {
		super();
		this.notaNegociacao = notaNegociacao;
		this.enumTipoOperacao = enumTipoOperacao;
		this.prazo = prazo;
		this.enumTipoMercado = enumTipoMercado;
		this.enumTipoAcao = enumTipoAcao;
		this.empresa = empresa;
		this.dayTrade = dayTrade;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
		this.precoTotal = precoTotal;
		this.enumTipoItem = enumTipoItem;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public NotaNegociacao getNotaNegociacao() {
		return this.notaNegociacao;
	}

	public void setNotaNegociacao(NotaNegociacao notaNegociacao) {
		this.notaNegociacao = notaNegociacao;
	}

	public EnumTipoOperacao getEnumTipoOperacao() {
		return this.enumTipoOperacao;
	}

	public void setEnumTipoOperacao(EnumTipoOperacao enumTipoOperacao) {
		this.enumTipoOperacao = enumTipoOperacao;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public EnumTipoMercado getEnumTipoMercado() {
		return this.enumTipoMercado;
	}

	public void setEnumTipoMercado(EnumTipoMercado enumTipoMercado) {
		this.enumTipoMercado = enumTipoMercado;
	}

	public EnumTipoAcao getEnumTipoAcao() {
		return this.enumTipoAcao;
	}

	public void setEnumTipoAcao(EnumTipoAcao enumTipoAcao) {
		this.enumTipoAcao = enumTipoAcao;
	}

	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return this.precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getPrecoTotal() {
		return this.precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

	public EnumTipoItem getEnumTipoItem() {
		return this.enumTipoItem;
	}

	public void setEnumTipoItem(EnumTipoItem enumTipoItem) {
		this.enumTipoItem = enumTipoItem;
	}

	public Boolean getDayTrade() {
		return dayTrade;
	}

	public void setDayTrade(Boolean dayTrade) {
		this.dayTrade = dayTrade;
	}

}
