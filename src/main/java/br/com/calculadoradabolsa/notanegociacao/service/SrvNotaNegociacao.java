package br.com.calculadoradabolsa.notanegociacao.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.calculadoradabolsa.notanegociacao.model.Competencia;
import br.com.calculadoradabolsa.notanegociacao.model.NotaNegociacao;
import br.com.calculadoradabolsa.notanegociacao.repository.CompetenciaRepository;
import br.com.calculadoradabolsa.notanegociacao.repository.NotaNegociacaoRepository;
import br.com.calculadoradabolsa.utils.SrvPadrao;
import br.com.calculadoradabolsa.utils.Utils;

/**
 * 
 * @author Daniel Oliveira
 * 
 *         calculadoradabolsa: 28-novembro-2019 12:43
 *
 *         Todos os direitos reservados. Nenhuma parte de código desse projeto
 *         pode ser copiada sem autorização expressa para quaisquer fins.
 */
@Service
public class SrvNotaNegociacao extends SrvPadrao<NotaNegociacao, NotaNegociacaoRepository>
		implements ISrvNotaNegociacao {

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;

	@Autowired
	CompetenciaRepository competenciaRepository;

	@Override
	@PostConstruct
	public void init() {
		setRepositorio(this.notaNegociacaoRepository);
	}

	@Override
	public Page<NotaNegociacao> filtrar(NotaNegociacao notaNegociacao, Pageable pageable) {
		return this.notaNegociacaoRepository.filtrar(notaNegociacao, pageable);
	}

	@Override
	public List<NotaNegociacao> salvarLista(List<NotaNegociacao> lstNotaNegociacao) {

		if (lstNotaNegociacao != null && !lstNotaNegociacao.isEmpty()) {
			Collections.sort(lstNotaNegociacao);

			NotaNegociacao ntNegoc = lstNotaNegociacao.get(0);
			Long cpt = Utils.getCompetenciaByData(ntNegoc.getDataPregao());

			NotaNegociacao ntNegocSalva = this.notaNegociacaoRepository.findFirstByCompetenciaAndCpf(cpt,
					ntNegoc.getCpf());

			if (ntNegocSalva == null) {
				this.competenciaRepository.save(new Competencia(cpt, ntNegoc.getCpf(), false));

				for (NotaNegociacao notaNegociacao : lstNotaNegociacao) {
					this.salvar(notaNegociacao);
				}
			}
		}
		return lstNotaNegociacao;
	}

}
