package br.com.calculadoradabolsa.notanegociacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.calculadoradabolsa.notanegociacao.model.Competencia;

public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {

  // Query Methods SpringJPA
  Competencia findByCompetencia( Long cpt );

}
