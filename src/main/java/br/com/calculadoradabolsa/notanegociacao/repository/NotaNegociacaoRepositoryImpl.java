package br.com.calculadoradabolsa.notanegociacao.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.calculadoradabolsa.notanegociacao.model.NotaNegociacao;

public class NotaNegociacaoRepositoryImpl implements NotaNegociacaoRepositoryQueries {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<NotaNegociacao> filtrar( NotaNegociacao notaNegociacao, Pageable pageable ) {
    // TODO Auto-generated method stub
    return null;
  }

}
