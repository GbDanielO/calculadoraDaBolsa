package br.com.calculadoradabolsa.utils;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 17-novembro-2019 12:59
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
@MappedSuperclass
public class EntidadePadrao implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3800019518730857787L;

  public EntidadePadrao() {
  }

  /**
   * pega o id da Entidade.
   * 
   * @author Daniel Oliveira - 30 de mar de 2018
   * 
   * @return the codigo.
   */
  @Transient
  public Long getId() {
    final Long id = ReflectionsUtils.getPropertyIdValue( this );
    return id;
  }

  /**
   * seta o id da Entidade.
   * 
   * @author Daniel Oliveira - 30 de mar de 2018
   *
   * @param codigo
   */
  @Transient
  public void setId( final Long id ) {
    ReflectionsUtils.setPropertyIdValue( this, id );
  }

  @Override
  public boolean equals( final Object obj ) {
    // testa por referencia ao null
    if ( obj == null ) {
      return false;
    }

    // testa por referencia
    if ( this == obj ) {
      return true;
    }

    // testa por referencia a classe
    // se a classe do argumento for diferente da minha classe.
    if ( !getClass().equals( obj.getClass() ) ) {
      return false;
    }

    // testa pelo codigo
    final Long meuCodigo = getId();
    final Long argCodigo = ( (EntidadePadrao) obj ).getId();

    // se o codigo for igual retorna true.
    if ( meuCodigo != null ) {
      return meuCodigo.equals( argCodigo );
    }

    return false;
  }

  /**
   * Pega o codigo do objeto via reflection e gera um hashcode para ele.
   * 
   */
  @Override
  public int hashCode() {
    final int PRIME = 37;
    int result = 1;

    final Long codigo = getId();

    result = PRIME * result + ( codigo == null ? 0 : codigo.hashCode() );

    return result;
  }

}
