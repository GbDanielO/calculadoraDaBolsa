package br.com.calculadoradabolsa.notanegociacao.enums;

/**
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 20-novembro-2019 11:58
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
public enum EnumResumoDosNegocios {

  DEBENTURES(1, "Debêntures"),
  VENDAS_A_VISTA(2, "Vendas à vista"),
  COMPRAS_A_VISTA(3, "Compras à vista"),
  OPCOES_COMPRAS(4, "Opções - compras"),
  OPCOES_VENDAS(5, "Opções - vendas"),
  COMPRAS_A_TERMO(6, "Operações à termo"),
  VALOR_OPER_COM_TITULOS_PUBLICOS(7, "Valor das oper. c/ títulos públ. (v. nom.)"),
  VALOR_TOTAL_DAS_OPERACOES(8, "Valor das operações");

  /**
   * Ordem conforme folha de corretagem, ao settar esse campo a partir da leitura do PDF a ordem deve ser rigorozamente
   * seguida. Na exibição dos dados também para poder facilitar a comparação com o PDF da corretora.
   * Esse campo serve mais para guiar o desenvolvedor nas operações.
   */
  private int ordem;

  /**
   * Descricao conforme folha de corretagem
   */
  private String descricao;

  private EnumResumoDosNegocios( int ordem, String descricao ) {
    this.ordem = ordem;
    this.descricao = descricao;
  }

  public int getOrdem() {
    return this.ordem;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public static EnumResumoDosNegocios valueOf( final int ordem ) {
    EnumResumoDosNegocios[] lstEnum = EnumResumoDosNegocios.values();

    for ( EnumResumoDosNegocios enumResumoDosNegocios : lstEnum ) {
      if ( enumResumoDosNegocios.getOrdem() == ordem ) {
        return enumResumoDosNegocios;
      }
    }
    return null;
  }
}
