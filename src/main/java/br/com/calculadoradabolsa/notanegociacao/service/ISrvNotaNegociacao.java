package br.com.calculadoradabolsa.notanegociacao.service;

import java.util.List;

import br.com.calculadoradabolsa.notanegociacao.model.NotaNegociacao;
import br.com.calculadoradabolsa.notanegociacao.repository.NotaNegociacaoRepository;
import br.com.calculadoradabolsa.utils.ISrvPadrao;

/**
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 28-novembro-2019 12:43
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
public interface ISrvNotaNegociacao extends ISrvPadrao<NotaNegociacao, NotaNegociacaoRepository> {

  List<NotaNegociacao> salvarLista( List<NotaNegociacao> lstNotaNegociacao );

}
