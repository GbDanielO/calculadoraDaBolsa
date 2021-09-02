package br.com.calculadoradabolsa.notanegociacao.enums;

/**
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 23-novembro-2019 07:10
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
public enum EnumResumoFinanceiro {

  VALOR_LIQUIDO_OPERACOES(1, "Valor líquido das operações"),
  TAXA_LIQUIDACAO(2, "Taxa de liquidação"),
  TAXA_REGISTRO(3, "Taxa de Registro"),
  TOTAL_CBLC(4, "Total CBLC"),
  TAXA_TERMO_OU_OPCOES(5, "Taxa de termo/opções"),
  TAXA_ANA(6, "Taxa A.N.A."),
  EMOLUMENTOS(7, "Emolumentos"),
  TOTAL_BOVESPA(8, "Total Bovespa / Soma"),
  TAXA_OPERACIONAL(9, "Taxa Operacional"),
  EXECUCAO(10, "Execução"),
  TAXA_CUSTODIA(11, "Taxa de Custódia"),
  IMPOSTOS(12, "Impostos"),
  IRRF(13, "I.R.R.F. s/ operações, base"),
  IRPF_DAY_TRADE(14, "IRRF Day Trade: Base"),
  OUTROS(15, "Outros"),
  TOTAL_CORRETAGEM_DESPESAS(16, "Total corretagem / Despesas"),
  LIQUIDO_PARA(17, "Líquido para");

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

  private EnumResumoFinanceiro( int ordem, String descricao ) {
    this.ordem = ordem;
    this.descricao = descricao;
  }

  public int getOrdem() {
    return this.ordem;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public static EnumResumoFinanceiro valueOf( final int ordem ) {
    EnumResumoFinanceiro[] lstEnum = EnumResumoFinanceiro.values();

    for ( EnumResumoFinanceiro enumResumoFinanceiro : lstEnum ) {
      if ( enumResumoFinanceiro.getOrdem() == ordem ) {
        return enumResumoFinanceiro;
      }
    }
    return null;
  }

}
