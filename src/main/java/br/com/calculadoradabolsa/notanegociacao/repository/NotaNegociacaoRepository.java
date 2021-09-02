package br.com.calculadoradabolsa.notanegociacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.calculadoradabolsa.notanegociacao.model.NotaNegociacao;

/**
 * 
 * @author Daniel Oliveira
 * 
 *         calculadoradabolsa: 28-novembro-2019 12:21
 *
 *         Todos os direitos reservados. Nenhuma parte de código desse projeto
 *         pode ser copiada sem autorização expressa para quaisquer fins.
 */
public interface NotaNegociacaoRepository extends JpaRepository<NotaNegociacao, Long>, NotaNegociacaoRepositoryQueries {

	// Query Methods SpringJPA
	NotaNegociacao findFirstByCompetenciaAndCpf(Long cpt, String cpf);

}
