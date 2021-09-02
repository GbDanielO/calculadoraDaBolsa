package br.com.calculadoradabolsa.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 17-novembro-2019 12:55
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
public class PageWrapper<T> {

  private T entidade;

  private final Page<T> page;

  private final UriComponentsBuilder uriBuilder;

  private final UriComponentsBuilder uriBuilderNoParams;

  /**
   * 
   * @author Daniel Oliveira - 09 de jun de 2018
   *
   * @param page
   * @param entidade
   */
  @SuppressWarnings("unchecked")
  public PageWrapper( final Page<T> page, final T entidade ) {
    this.page = page != null ? page : (Page<T>) PageRequest.of( 0, 20 );

    this.entidade = entidade;

    this.uriBuilderNoParams = ServletUriComponentsBuilder.fromCurrentRequestUri();

    /*
     * Se quisesse montar o cara já com os parâmetros da URL poderia usar da seguinte forma:
     * 
     * this.uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
     * 
     * Porém para poder tirar parâmetros nulos preferi pegá-los separadamente.
     */
    this.uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();

    final MultiValueMap<String, String> queryParams =
        ServletUriComponentsBuilder.fromCurrentRequest().build().getQueryParams();

    final MultiValueMap<String, String> newQueryParams = removerParamsNull( queryParams );

    this.uriBuilder.replaceQueryParams( newQueryParams ).build();
  }

  /**
   * 
   * @author Daniel Oliveira - 14 de jun de 2018
   *
   * @param queryParams
   * @return MultiValueMap<String, String>
   */
  private MultiValueMap<String, String> removerParamsNull( final MultiValueMap<String, String> queryParams ) {

    final MultiValueMap<String, String> newQueryParams = new LinkedMultiValueMap<>();

    for ( final Entry<String, List<String>> entry : queryParams.entrySet() ) {
      if ( entry.getValue() != null && !entry.getValue().isEmpty() && entry.getValue().get( 0 ) != null
          && !entry.getValue().get( 0 ).isEmpty() && entry.getValue().get( 0 ).length() >= 1 ) {
        newQueryParams.add( entry.getKey(), entry.getValue().get( 0 ) );
      }
    }
    return newQueryParams;
  }

  public List<T> getContent() {
    return this.page.getContent();
  }

  public boolean hasContent() {
    return this.page.hasContent();
  }

  public Integer getSize() {
    return this.page.getSize();
  }

  public boolean hasPrevious() {
    return this.page.hasPrevious();
  }

  public boolean hasNext() {
    return this.page.hasNext();
  }

  public int getNumber() {
    return this.page.getNumber();
  }

  public boolean isFirst() {
    return this.page.isFirst();
  }

  public boolean isLast() {
    return this.page.isLast();
  }

  public int getTotalPages() {
    return this.page.getTotalPages();
  }

  public String urlToPage() {
    return this.uriBuilder.replaceQueryParam( "page", this.page.getNumber() )
        .replaceQueryParam( "size", this.page.getSize() ).build( true ).encode().toUriString();
  }

  public String urlToPage( final Integer pagina, Integer size ) {
    size = size != null ? size : 20;
    return this.uriBuilder.replaceQueryParam( "page", pagina ).replaceQueryParam( "size", size ).build( true ).encode()
        .toUriString();
  }

  public String urlNoParamsToPage() {
    return this.uriBuilderNoParams.replaceQueryParam( "page", this.page.getNumber() )
        .replaceQueryParam( "size", this.page.getSize() ).build( true ).encode().toUriString();
  }

  public String urlOrdered( final String propriedade ) {
    final UriComponentsBuilder uriBuilderOrder =
        UriComponentsBuilder.fromUriString( this.uriBuilder.build( true ).encode().toUriString() );

    final String valorSort = String.format( "%s,%s", propriedade, invertDirection( propriedade ) );

    return uriBuilderOrder.replaceQueryParam( "sort", valorSort ).build( true ).encode().toUriString();
  }

  public String invertDirection( final String propriedade ) {
    String direcao = "asc";

    final Order order = this.page.getSort() != null ? this.page.getSort().getOrderFor( propriedade ) : null;
    if ( order != null ) {
      direcao = Sort.Direction.ASC.equals( order.getDirection() ) ? "desc" : "asc";
    }

    return direcao;
  }

  public boolean descendente( final String propriedade ) {
    return invertDirection( propriedade ).equals( "asc" );
  }

  public boolean ordered( final String propriedade ) {
    final Order order = this.page.getSort() != null ? this.page.getSort().getOrderFor( propriedade ) : null;

    if ( order == null ) {
      return false;
    }

    return this.page.getSort().getOrderFor( propriedade ) != null ? true : false;
  }

  public List<Integer> getListTotalPerPage() {
    final List<Integer> lst = new ArrayList<>();
    lst.add( 5 );
    lst.add( 10 );
    lst.add( 20 );
    lst.add( 30 );
    lst.add( 50 );
    return lst;
  }

  public T getEntidade() {
    return this.entidade;
  }

  public void setEntidade( final T entidade ) {
    this.entidade = entidade;
  }

}
