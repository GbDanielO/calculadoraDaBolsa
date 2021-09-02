package br.com.calculadoradabolsa.utils;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Daniel Oliveira - 06 de maio de 2018
 *
 * @param <Entidade>
 * @param <Repositorio>
 */
public interface ISrvPadrao<Entidade, Repositorio extends JpaRepository<Entidade, Long>> {

    public void init();

    public Entidade salvar( Entidade entidade );

    public Optional<Entidade> buscarPeloCodigo( Long codigo );

    public Collection<Entidade> buscarTodos();

    public Page<Entidade> filtrar( Entidade entidade, Pageable pageable );

    public void deletar( Long codigo );
}
