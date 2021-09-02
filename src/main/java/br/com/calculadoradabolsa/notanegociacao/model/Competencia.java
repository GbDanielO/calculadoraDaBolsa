package br.com.calculadoradabolsa.notanegociacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.calculadoradabolsa.utils.EntidadePadrao;

@Entity
@Table(name = "t_competencia")
public class Competencia extends EntidadePadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4530527737854067939L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_competencia")
	private Long id;

	private Long competencia;

	private String cpf;

	@Column(name = "flg_encerrada")
	private Boolean flgEncerrada;

	public Competencia() {
	}

	public Competencia(Long competencia, String cpf, Boolean flgEncerrada) {
		super();
		this.competencia = competencia;
		this.cpf = cpf;
		this.flgEncerrada = flgEncerrada;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompetencia() {
		return this.competencia;
	}

	public void setCompetencia(Long competencia) {
		this.competencia = competencia;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getFlgEncerrada() {
		return this.flgEncerrada;
	}

	public void setFlgEncerrada(Boolean flgEncerrada) {
		this.flgEncerrada = flgEncerrada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((competencia == null) ? 0 : competencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Competencia other = (Competencia) obj;
		if (competencia == null) {
			if (other.competencia != null)
				return false;
		} else if (!competencia.equals(other.competencia))
			return false;
		return true;
	}

}
