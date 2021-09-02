package br.com.calculadoradabolsa.upload.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.calculadoradabolsa.notanegociacao.enums.EnumResumoDosNegocios;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumResumoFinanceiro;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoAcao;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoItem;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoMercado;
import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoOperacao;
import br.com.calculadoradabolsa.notanegociacao.model.NegocioRealizado;
import br.com.calculadoradabolsa.notanegociacao.model.NotaNegociacao;
import br.com.calculadoradabolsa.notanegociacao.model.ResumoFinanceiro;
import br.com.calculadoradabolsa.notanegociacao.model.ResumoNegocios;
import br.com.calculadoradabolsa.notanegociacao.service.ISrvNotaNegociacao;
import br.com.calculadoradabolsa.utils.Utils;

@Controller
@RequestMapping("/upload")
public class UploadController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2135354682903807735L;

	// substituir por uma forma de pegar a senha do PDF
	// que pode ser do cadastro do usuário ou pedindo para ele informar
	private String senhaPDF = "027";

	@Autowired
	private ISrvNotaNegociacao srvNotaNegociacao;

	private static final String rxCPF = "(?ims)\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";

	private static final String rxCliente = "(?ims)^.*digo\\s{1}cliente.*";

	private static final String rxNumeroNota = "(?ims)^Nr\\.\\s{1}nota";

	private static final String rxFolha = "(?ims)^Folha";

	private static final String rxDataPregao = "(?ims)^Data\\s{1}preg.{1}o";

	private static final String rxNegocioRealizado = "(?ims)Neg.{1,2}cios\\s{1}realizados";

	private static final String rxIRRFDayTrade = "(?ims)^IRRF\\sDay\\sTrade.*";

	private static final String rxIniciaComUmDigitoNumerico = "(?ims)^\\d{1,}.*";

	private static final String rxTerminaComVirgulaEDoisDigitosNumericos = "(?ims).*,\\d{2}$";

	private static final String rxValorLiquidoOperacoes = "(?ims).*Valor\\sl.{1}quido\\sdas\\sopera.{2}es\\s[cd]$";

	private static final String rxTaxaLiquidacao = "(?ims).*Taxa\\sde\\sliquida.{2}o\\s[cd]$";

	private static final String rxTaxaRegistro = "(?ims).*Taxa\\sde\\sRegistro\\s[cd]$";

	private static final String rxTotalCBLC = "(?ims).*Total\\sCBLC\\s[cd]$";

	private static final String rxTaxaTermosOuOpcoes = "(?ims).*Taxa\\sde\\stermo/op.{2}es\\s[cd]$";

	private static final String rxTaxaANA = "(?ims).*Taxa\\sA\\.N\\.A\\.\\s[cd]$";

	private static final String rxEmolumentos = "(?ims).*Emolumentos\\s[cd]$";

	private static final String rxTotalBovespa = "(?ims).*Total\\sBovespa\\s/\\sSoma\\s[cd]$";

	private static final String rxTaxaOperacional = "(?ims).*Taxa Operacional\\s[cd]$";

	private static final String rxExecucao = "(?ims).*Execu.{2}o$";

	private static final String rxTaxaCustodia = "(?ims).*Taxa\\sde\\sCust.{1}dia$";

	private static final String rxImpostos = "(?ims).*Impostos$";

	private static final String rxIRRFOperacoes = "(?ims).*I\\.R\\.R\\.F\\.\\ss/\\sopera.{1,3}es,\\sbase.*";

	private static final String rxOutros = "(?ims).*Outros\\s[cd]$";

	private static final String rxTotalCorretagem = "(?ims).*Total\\scorretagem\\s/\\sDespesas\\s[cd]$";

	private static final String rxLiquidoPara = "(?ims).*L.{1}quido\\spara\\s\\d{2}/\\d{2}/\\d{4}\\s[cd]$";

	@RequestMapping("")
	public String carregaPaginaUpload() {
		return "upload/upload";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String uploadNotaNegociacao(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		executaUpload(file);

		redirectAttributes.addFlashAttribute("message",
				"Nota de corretagem importada com sucesso! " + file.getOriginalFilename());

		return "redirect:/upload";
	}

	private void executaUpload(MultipartFile file) {
		PDDocument pdfDocument;
		PDFTextStripper pdfStripper = null;

		try {
			pdfDocument = PDDocument.load(file.getBytes(), senhaPDF);
			int numberOfPages = pdfDocument.getNumberOfPages();
			pdfStripper = new PDFTextStripper();

			List<NotaNegociacao> lstNotaNegociacao = new ArrayList<>();
			for (int i = 1; i <= numberOfPages; i++) {

				NotaNegociacao notaNegociacao = new NotaNegociacao();
				notaNegociacao.setLstNegocioRealizado(new ArrayList<>());
				List<ResumoFinanceiro> lstResumoFinanceiro = new ArrayList<>();
				notaNegociacao.setLstResumoNegocios(new ArrayList<>());

				pdfStripper.setStartPage(i);
				pdfStripper.setEndPage(i);
				String parsedText = pdfStripper.getText(pdfDocument);

				System.out.println(parsedText);
				System.out.println();

				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------");
				System.out.println();

				InputStream is = new ByteArrayInputStream(parsedText.getBytes());
				final InputStreamReader inputStreamReader = new InputStreamReader(is);
				LineNumberReader reader = new LineNumberReader(inputStreamReader);

				String a;

				do {
					a = reader.readLine();

					if (a != null) {

						/*
						 * Preenche cabeçalho da nota de negociação.
						 */
						if (a.matches(rxCliente)) {
							a = reader.readLine();
							System.out.println("Cliente" + "   " + a);
							continue;
						}

						if (a.matches(rxCPF)) {
							notaNegociacao.setCpf(a.replaceAll("\\D", ""));
							System.out.println("CPF" + "   " + a);
							continue;
						}

						if (a.matches(rxNumeroNota)) {
							a = reader.readLine();
							notaNegociacao.setNumeroNota(Long.parseLong(a));
							System.out.println("Número nota" + "   " + a);
							continue;
						}

						if (a.matches(rxFolha)) {
							a = reader.readLine();
							System.out.println("Folha" + "   " + a);
							continue;
						}

						if (a.matches(rxDataPregao)) {
							a = reader.readLine();
							notaNegociacao.setDataPregao(Utils.getDataSemHoraAPartirDaString(a));
							System.out.println("Data Pregão" + "   " + a);
							continue;
						}
						/*
						 * fim cabeçalho da nota de negociação.
						 */

						/*
						 * Preenche a lista de negócios realizados.
						 */
						if (a.matches(rxNegocioRealizado)) {
							System.out.println("Negocio Realizado" + "   ");
							a = reader.readLine();
							a = reader.readLine();
							while (a.matches("(?i)^\\d{1,}-.*") && a.matches("(?ims).*\\s[cd]$")) {

								NegocioRealizado negocioRealizado = new NegocioRealizado();

								// seta o tipo de operação
								negocioRealizado.setEnumTipoOperacao(
										EnumTipoOperacao.valueOf(Utils.getStringByRegex(a, "(?i)\\s[cv]\\s").trim()));

								String tipoMercado = Utils.getStringByRegex(a,
										EnumTipoMercado.FRACIONARIO.getDescricao());

								if (tipoMercado == null) {
									tipoMercado = Utils.getStringByRegex(a, EnumTipoMercado.VISTA.getDescricao());
								}

								if (tipoMercado == null) {
									tipoMercado = Utils.getStringByRegex(a,
											EnumTipoMercado.OPCAO_DE_COMPRA.getDescricao()) != null
													? EnumTipoMercado.OPCAO_DE_COMPRA.toString()
													: null;
								}

								negocioRealizado.setEnumTipoMercado(EnumTipoMercado.valueOf(tipoMercado.trim()));

								negocioRealizado.setPrazo(Utils.getStringByRegex(a, "\\s\\d{2}/\\d{2}\\s").trim());

								// seta o tipo de mercado
								String tipoAcao = Utils.getStringByRegex(a,
										"(?i)\\s" + EnumTipoAcao.ON.getDescricao() + "\\s");

								if (tipoAcao == null) {
									tipoAcao = Utils.getStringByRegex(a,
											"(?i)\\s" + EnumTipoAcao.PN.getDescricao() + "\\s");
								}

								if (tipoAcao == null) {
									tipoAcao = Utils.getStringByRegex(a,
											"(?i)\\s" + EnumTipoAcao.UNT.getDescricao() + "\\s");
								}

								if (tipoAcao == null) {
									tipoAcao = Utils.getStringByRegex(a,
											"(?i)\\s" + EnumTipoAcao.BRUNT.getDescricao() + "\\s");
								}

								if (tipoAcao == null) {
									tipoAcao = Utils.getStringByRegex(a,
											"(?i)\\s" + EnumTipoAcao.PNA.getDescricao() + "\\s");
								}

								if (tipoAcao == null) {
									tipoAcao = Utils.getStringByRegex(a,
											"(?i)\\s" + EnumTipoAcao.PNB.getDescricao() + "\\s");
								}

								if (tipoAcao == null) {
									tipoAcao = Utils.getStringByRegex(a,
											"(?i)\\s" + EnumTipoAcao.PNC.getDescricao() + "\\s");
								}

								if (tipoAcao == null) {
									tipoAcao = Utils.getStringByRegex(a,
											"(?i)\\s" + EnumTipoAcao.PND.getDescricao() + "\\s");
								}

								if (tipoAcao == null) {
									tipoAcao = Utils.getStringByRegex(a,
											"(?i)\\s" + EnumTipoAcao.CI.getDescricao() + "\\s");
								}

								if (tipoAcao == null) {
									tipoAcao = EnumTipoAcao.FIL.getDescricao();
								}

								negocioRealizado.setEnumTipoAcao(EnumTipoAcao.valueOf(tipoAcao.trim()));

								// seta a empresa a qual compramos a ação
								String empresa = "";
								if (negocioRealizado.getEnumTipoAcao().equals(EnumTipoAcao.FIL)) {
									empresa = a.substring(a.indexOf(tipoMercado) + tipoMercado.length());
									negocioRealizado.setEmpresa(empresa
											.substring(0, empresa.indexOf(Utils.getStringByRegex(empresa,
													"\\s\\d{1,}\\s\\d{1,}\\s|\\s\\w{1,}\\s\\d{1,}\\s|\\s\\w{1,}#\\s|\\s#\\s")))
											.trim());
								} else {
									empresa = a
											.substring(a.indexOf(negocioRealizado.getEnumTipoMercado().getDescricao())
													+ negocioRealizado.getEnumTipoMercado().getDescricao().length());

									negocioRealizado
											.setEmpresa(empresa.substring(negocioRealizado.getPrazo() != null ? 6 : 0,
													empresa.indexOf(tipoAcao)).trim());
								}

								// seta observação
								String observacao = Utils.getStringByRegex(empresa,
										"\\s\\d{1,}\\s\\d{1,}\\s|\\s\\w{1,}\\s\\d{1,}\\s|\\s\\w{1,}#\\s|\\s#\\s");
								if (observacao != null) {
									observacao = observacao.trim();
									if (observacao.contains("D")) {
										negocioRealizado.setDayTrade(true);
									} else {
										negocioRealizado.setDayTrade(false);
									}
								}

								// seta a quantidade de ações
								a = a.replaceAll("\\.", "");
								String qtdAcoes = Utils.getStringByRegex(a, "\\s\\d{1,}\\s");
								negocioRealizado.setQuantidade(Integer.parseInt(qtdAcoes.trim()));

								// seta o preco unitario
								String substringDeA = a.substring(a.indexOf(qtdAcoes) + qtdAcoes.length());
								substringDeA = substringDeA.replaceAll("\\.", "").replaceAll(",", "\\.");

								negocioRealizado.setPrecoUnitario(BigDecimal.valueOf(Double
										.parseDouble(substringDeA.substring(0, substringDeA.indexOf(" ")).trim())));

								// seta valor total
								negocioRealizado.setPrecoTotal(BigDecimal.valueOf(Double.parseDouble(substringDeA
										.substring(substringDeA.indexOf(" "), substringDeA.lastIndexOf(" ")).trim())));

								// seta enumTipoItem
								negocioRealizado.setEnumTipoItem(
										EnumTipoItem.valueOf(Utils.getStringByRegex(a, "(?i)\\s[cd]$").trim()));

								System.out.println(a);

								// adciona a qual nota ele pertence
								negocioRealizado.setNotaNegociacao(notaNegociacao);
								// adiciona o negocio realizado na lista
								notaNegociacao.getLstNegocioRealizado().add(negocioRealizado);

								// lê a proxima linha
								a = reader.readLine();
							}
							System.out.println();
							// adiciona a lista de negócio realizado a nota de negociação.

							continue;
						}
						/*
						 * fim da lista de negócios realizados.
						 */

						/*
						 * Preenche a lista de Resumo dos Negócios
						 */
						int j = 1;
						while (a.matches(rxIniciaComUmDigitoNumerico)
								&& a.matches(rxTerminaComVirgulaEDoisDigitosNumericos) && !a.matches(rxIRRFOperacoes)) {
							ResumoNegocios resumoNegocios = new ResumoNegocios();

							resumoNegocios.setEnumResumoDosNegocios(EnumResumoDosNegocios.valueOf(j));
							resumoNegocios.setValor(BigDecimal.valueOf(
									Double.parseDouble(a.replaceAll("\\.", "").replaceAll(",", "\\.").trim())));
							resumoNegocios.setNotaNegociacao(notaNegociacao);

							notaNegociacao.getLstResumoNegocios().add(resumoNegocios);

							System.out.print(EnumResumoDosNegocios.valueOf(j).getDescricao() + "   ");
							System.out.println(a);

							// lê a proxima linha
							a = reader.readLine();
							j++;
						}
						/*
						 * fim da lista de Resumo dos Negócios
						 */

						/*
						 * Preenche a lista Resumo Financeiro
						 */
						if (a.matches(rxValorLiquidoOperacoes)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.VALOR_LIQUIDO_OPERACOES);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxTaxaLiquidacao)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TAXA_LIQUIDACAO);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxTaxaRegistro)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TAXA_REGISTRO);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxTotalCBLC)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TOTAL_CBLC);

							System.out.println(a);
							continue;
						}

						if (a.matches(rxTaxaTermosOuOpcoes)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TAXA_TERMO_OU_OPCOES);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxTaxaANA)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TAXA_ANA);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxEmolumentos)) {
							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.EMOLUMENTOS);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxTotalBovespa)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TOTAL_BOVESPA);

							System.out.println(a);
							continue;
						}

						if (a.matches(rxTaxaOperacional)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TAXA_OPERACIONAL);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxExecucao)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.EXECUCAO);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxTaxaCustodia)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TAXA_CUSTODIA);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxImpostos)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.IMPOSTOS);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxIRRFOperacoes)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a, EnumResumoFinanceiro.IRRF);

							System.out.println(a);
							continue;
						}
						if (a.matches(rxIRRFDayTrade)) {
							ResumoFinanceiro resumoFinanceiro = new ResumoFinanceiro();

							String strValor = a.substring(a.lastIndexOf(" "));

							resumoFinanceiro.setValor(BigDecimal.valueOf(
									Double.parseDouble(strValor.replaceAll("\\.", "").replaceAll(",", "\\.").trim())));

							resumoFinanceiro.setEnumResumoFinanceiro(EnumResumoFinanceiro.IRPF_DAY_TRADE);

							resumoFinanceiro.setNotaNegociacao(notaNegociacao);
							lstResumoFinanceiro.add(resumoFinanceiro);
							System.out.println(a);
							continue;
						}
						if (a.matches(rxOutros)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.OUTROS);

							System.out.println(a);
							continue;
						}

						if (a.matches(rxTotalCorretagem)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.TOTAL_CORRETAGEM_DESPESAS);

							System.out.println(a);
							continue;
						}

						if (a.matches(rxLiquidoPara)) {

							preencheResumoFinanceiro(notaNegociacao, lstResumoFinanceiro, a,
									EnumResumoFinanceiro.LIQUIDO_PARA);

							System.out.println(a);
							continue;
						}
						/*
						 * Fim da lista Resumo Financeiro
						 */
					}
				} while (a != null);
				// a lista de resumo financeiro só deve ser adicionada a nota de negociação ao
				// final da leitura da página porque
				// esses dados não são preenchido em um laço de repetição a parte como os
				// negócios realizados e o resumo dos
				// negócios.
				notaNegociacao.setLstResumoFinanceiro(lstResumoFinanceiro);

				is.close();
				inputStreamReader.close();
				notaNegociacao.setCompetencia(Utils.getCompetenciaByData(notaNegociacao.getDataPregao()));
				lstNotaNegociacao.add(notaNegociacao);

			}

			pdfDocument.close();

			this.srvNotaNegociacao.salvarLista(lstNotaNegociacao);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void preencheResumoFinanceiro(NotaNegociacao notaNegociacao, List<ResumoFinanceiro> lstResumoFinanceiro,
			String a, EnumResumoFinanceiro enumResumoFinanceiro) {

		ResumoFinanceiro resumoFinanceiro = new ResumoFinanceiro();

		String strValor = a.substring(0, a.indexOf(Utils.getStringByRegex(a, "(?i)[a-z]{1,}")));

		resumoFinanceiro.setValor(
				BigDecimal.valueOf(Double.parseDouble(strValor.replaceAll("\\.", "").replaceAll(",", "\\.").trim())));

		resumoFinanceiro.setEnumResumoFinanceiro(enumResumoFinanceiro);

		String strTipoItem = Utils.getStringByRegex(a, "(?i)\\s[cd]$");

		if (strTipoItem != null && !strTipoItem.isEmpty()) {
			strTipoItem = strTipoItem.trim();
			resumoFinanceiro.setEnumTipoItem(EnumTipoItem.valueOf(strTipoItem));
		}

		resumoFinanceiro.setNotaNegociacao(notaNegociacao);
		lstResumoFinanceiro.add(resumoFinanceiro);
	}
}
