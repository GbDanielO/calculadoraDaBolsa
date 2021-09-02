package br.com.calculadoradabolsa.notanegociacao.enums;

/**
 * Mostra se o item credita ou debita valor da conta na corretora.
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 23-novembro-2019 07:55
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
public enum EnumTipoItem {

  D("Débito"), C("Crédito");

  private EnumTipoItem( String descricao ) {
    this.descricao = descricao;
  }

  private String descricao;

  public String getDescricao() {
    return this.descricao;
  }
}
