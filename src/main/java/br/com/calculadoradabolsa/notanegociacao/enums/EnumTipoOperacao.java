package br.com.calculadoradabolsa.notanegociacao.enums;

/**
 * Mostra se a operação realizada com o papel é de compra ou venda.
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 23-novembro-2019 07:58
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
public enum EnumTipoOperacao {

  C("Compra"), V("Venda");

  private EnumTipoOperacao( String descricao ) {
    this.descricao = descricao;
  }

  private String descricao;

  public String getDescricao() {
    return this.descricao;
  }
}
