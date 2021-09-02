package br.com.calculadoradabolsa.utils;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author Daniel Oliveira - 14 de jun de 2018
 *
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn( LocalDate localDate ) {
        return ( localDate == null ? null : Date.valueOf( localDate ) );
    }

    @Override
    public LocalDate convertToEntityAttribute( Date sqlDate ) {
        return ( sqlDate == null ? null : sqlDate.toLocalDate() );
    }
}
