package br.com.calculadoradabolsa.utils;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.PostConstruct;

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
public abstract class SrvPadrao<Entidade, Repositorio extends JpaRepository<Entidade, Long>>
    implements ISrvPadrao<Entidade, Repositorio> {

  private Repositorio repositorio;

  private Entidade entidade;

  public SrvPadrao() {
  }

  @Override
  @PostConstruct
  public abstract void init();

  @Override
  public Entidade salvar( Entidade entidade ) {
    this.repositorio.save( entidade );
    return entidade;
  }

  @Override
  public Optional<Entidade> buscarPeloCodigo( Long codigo ) {
    return this.repositorio.findById( codigo );
  }

  @Override
  public Collection<Entidade> buscarTodos() {
    return this.repositorio.findAll();
  }

  @Override
  public abstract Page<Entidade> filtrar( Entidade entidade, Pageable pageable );

  @Override
  public void deletar( Long codigo ) {
    this.repositorio.deleteById( codigo );

  }

  public Repositorio getRepositorio() {
    return this.repositorio;
  }

  public void setRepositorio( Repositorio repositorio ) {
    this.repositorio = repositorio;
  }

  public Entidade getEntidade() {
    return this.entidade;
  }

  public void setEntidade( Entidade entidade ) {
    this.entidade = entidade;
  }

}
