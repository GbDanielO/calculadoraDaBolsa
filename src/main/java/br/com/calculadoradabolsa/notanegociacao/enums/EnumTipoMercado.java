package br.com.calculadoradabolsa.notanegociacao.enums;

/**
 * Se a compra é fracionario, vista, etc
 * 
 * @author Daniel Oliveira
 * 
 *         calculadoradabolsa: 23-novembro-2019 10:43
 *
 *         Todos os direitos reservados. Nenhuma parte de código desse projeto
 *         pode ser copiada sem autorização expressa para quaisquer fins.
 */
public enum EnumTipoMercado {

	FRACIONARIO("FRACIONARIO"), VISTA("VISTA"), OPCAO_DE_COMPRA("OPCAO DE COMPRA");

	private String descricao;

	private EnumTipoMercado(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
