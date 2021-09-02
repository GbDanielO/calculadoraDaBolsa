package br.com.calculadoradabolsa.notanegociacao.enums;

/**
 * Tipo de Ação: ON, PN, Units, etc...
 * 
 * @author Daniel Oliveira
 * 
 *         calculadoradabolsa: 24-novembro-2019 02:00
 *
 *         Todos os direitos reservados. Nenhuma parte de código desse projeto
 *         pode ser copiada sem autorização expressa para quaisquer fins.
 */
public enum EnumTipoAcao {

	ON("ON"), PN("PN"), UNT("UNT"), BRUNT("BRUNT"), PNA("PNA"), PNB("PNB"), 
	PNC("PNC"), PND("PND"), FIL("FIL"), CI("CI");

	private String descricao;

	private EnumTipoAcao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
