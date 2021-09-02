package br.com.calculadoradabolsa.notanegociacao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.calculadoradabolsa.notanegociacao.model.NotaNegociacao;

public interface NotaNegociacaoRepositoryQueries {

  Page<NotaNegociacao> filtrar( NotaNegociacao notaNegociacao, Pageable pageable );

}
