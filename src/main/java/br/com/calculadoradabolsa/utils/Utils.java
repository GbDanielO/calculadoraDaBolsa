package br.com.calculadoradabolsa.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * 
 * @author Daniel Oliveira - 30 de mar de 2018
 *
 */
public class Utils {

  /**
   * 
   * @author Daniel Oliveira - 30 de mar de 2018
   *
   * @param mapWhere
   * @param query
   */
  public static void setarParametroWhere( final Map<String, Object> mapWhere, final Query query ) {
    if ( mapWhere != null && !mapWhere.isEmpty() ) {
      final Set<String> lstParametros = mapWhere.keySet();

      if ( lstParametros != null && !lstParametros.isEmpty() ) {
        for ( final String parametro : lstParametros ) {
          final Object valorKey = mapWhere.get( parametro );

          if ( valorKey.getClass().equals( Date.class ) ) {
            query.setParameter( parametro, (Date) valorKey, TemporalType.DATE );
          } else {
            query.setParameter( parametro, valorKey );
          }
        }
      }
    }
  }

  /**
   * 
   * @author Daniel Oliveira - 10 de jun de 2018
   *
   * @param stamentSql
   */
  public final static void removeAndOuWhereStamentSql( final StringBuilder stamentSql ) {
    // Remover o "WHERE" ou "AND".
    boolean endsWith = stamentSql.toString().trim().endsWith( "WHERE" );
    if ( endsWith ) {
      removeLastToken( stamentSql, "WHERE" );
    } else {
      endsWith = stamentSql.toString().trim().endsWith( "AND" );
      if ( endsWith ) {
        removeLastToken( stamentSql, "AND" );
      }
    }
  }

  /**
   * 
   * @author Daniel Oliveira - 9 de jun de 2018
   *
   * @param stamentSql
   * @param isWhere
   */
  public final static void removeAndOuWhereStamentSql( final StringBuilder stamentSql, final boolean isWhere ) {
    // Remover o "WHERE" ou "AND".
    if ( isWhere ) {
      final boolean endsWith = stamentSql.toString().trim().endsWith( "WHERE" );
      if ( endsWith ) {
        removeLastToken( stamentSql, "WHERE" );
      }
    } else {
      final boolean endsWith = stamentSql.toString().trim().endsWith( "AND" );
      if ( endsWith ) {
        removeLastToken( stamentSql, "AND" );
      }
    }
  }

  /**
   * 
   * @author Daniel Oliveira - 9 de jun de 2018
   *
   * @param statement
   * @param token
   */
  public static void removeLastToken( final StringBuilder statement, final String token ) {

    final int posBegin = statement.lastIndexOf( token );

    if ( posBegin != -1 ) {
      final int posEnd = statement.length();

      statement.delete( posBegin, posEnd );
    }
  }

  /**
   * 
   * @author Daniel Oliveira - 30 de mar de 2018
   *
   * @param date
   * @param mascara
   * @return String dataFormatada
   */
  public static String getDataFormatada( final Date date, final String mascara ) {
    if ( date != null ) {
      final SimpleDateFormat dateFormat = new SimpleDateFormat( mascara, new Locale( "pt", "BR" ) );

      return dateFormat.format( date );
    }
    return "";
  }

  /**
   * 
   * @author Daniel Oliveira - 30 de mar de 2018
   *
   * @param date
   * @return String dataFormatadaComHhora
   */
  public static String getDataFormataComHora( final Date date ) {
    if ( date != null ) {
      final DateFormat df = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

      return df.format( date );
    }
    return null;
  }

  /**
   * String no formato dd/MM/yyyy
   * 
   * @author Daniel Oliveira - 24 de nov de 2019
   *
   * @param data
   * @return
   */
  public static Date getDataSemHoraAPartirDaString( String data ) {

    if ( data == null || data.isEmpty() ) {
      return null;
    }

    data = data.replaceAll( "\\D", "" );

    Calendar c = Calendar.getInstance();

    c.set( Integer.parseInt( data.substring( 4, 8 ) ), Integer.parseInt( data.substring( 2, 4 ) ) - 1,
        Integer.parseInt( data.substring( 0, 2 ) ), 0, 0, 0 );

    return c.getTime();
  }

  /**
   * 
   * @author Daniel Oliveira - 30 de nov de 2019
   *
   * @param competencia
   * @return
   */
  public static String formatarCompetencia( final Long competencia ) {
    if ( competencia != null ) {
      final String tmp = competencia.toString();

      final StringBuilder mes = new StringBuilder();
      final StringBuilder ano = new StringBuilder();

      ano.append( tmp.substring( 0, 4 ) );
      mes.append( tmp.substring( 4, 6 ) );

      return mes + "/" + ano;

    }

    return null;
  }

  /**
   * 
   * @author Daniel Oliveira - 30 de nov de 2019
   *
   * @param dataParametro
   * @return Long yyyyMM
   */
  public static Long getCompetenciaByData( final Date dataParametro ) {
    final DateFormat formatoData = new SimpleDateFormat( "dd/MM/yyyy" );
    final String dataString = formatoData.format( dataParametro );
    return new Long( dataString.substring( dataString.length() - 4, dataString.length() )
        + dataString.substring( dataString.length() - 7, dataString.length() - 5 ) );

  }

  /**
   * 
   * @author Daniel Oliveira - 30 de nov de 2019
   *
   * @param data
   * @return MM/yyyy
   */
  public static String getCompetenciaByDataAsString( final Date data ) {
    if ( data != null ) {
      String mesData = null;

      final Calendar calendar = Calendar.getInstance();

      calendar.setTime( data );

      final int mes = calendar.get( Calendar.MONTH ) + 1;

      if ( mes < 10 ) {
        mesData = "0" + mes;
      } else {
        mesData = String.valueOf( mes );
      }

      final int ano = calendar.get( Calendar.YEAR );

      return mesData + "/" + ano;
    }
    return null;
  }

  /**
   * 
   * @author Daniel Oliveira - 24 de nov de 2019
   *
   * @param texto
   * @param regex
   */
  public static String getStringByRegex( String texto, String regex ) {

    if ( texto == null || texto.isEmpty() || regex == null || regex.isEmpty() ) {
      return null;
    }

    Pattern pattern = Pattern.compile( regex );
    Matcher matcher = pattern.matcher( texto );
    if ( matcher.find() ) {
      return matcher.group();
    }
    return null;
  }
}
