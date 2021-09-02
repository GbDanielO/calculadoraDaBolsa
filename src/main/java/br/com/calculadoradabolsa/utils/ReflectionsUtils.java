package br.com.calculadoradabolsa.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Id;

/**
 * 
 * @author Daniel Oliveira - 30 de mar de 2018
 *
 */
public class ReflectionsUtils {

    /**
     * Constante para indicar um metodo get.
     */
    public static final String GET_PREFIX = "get";

    /**
     * Indica um metodo is.
     */
    public static final String IS_PREFIX = "is";

    /**
     * indica um metodo set.
     */
    public static final String SET_PREFIX = "set";

    public static Long getPropertyIdValue( final Object bean ) {
        Long result = null;

        if ( bean != null ) {
            final String propertyName = getPropertyIdName( bean.getClass() );

            try {
                result = (Long) getGetMethod( propertyName, bean );
            } catch ( final Exception e ) {
                // ???
            }
        }

        return result;
    }

    public static void setPropertyIdValue( final Object bean, final Long value ) {
        final String propertyName = getPropertyIdName( bean.getClass() );

        try {
            setSetMethod( propertyName, bean, Long.class, value );
        } catch ( final Exception e ) {
            //
        }
    }

    public static void setSetMethod( final String property, final Object bean,
            final Class<?> paramtype, final Object value )
            throws IllegalArgumentException, NoSuchMethodException,
            InvocationTargetException {

        final String method = getMethodNameByPropertyName( SET_PREFIX, property );
        final Class<?>[] types =
                { paramtype };
        final Object[] args =
                { value };
        final Class<?> beanClass = bean.getClass();

        try {
            executeMethod( beanClass, bean, method, types, args );
        } catch ( final IllegalAccessException e ) {
            //
        }
    }

    public static String getPropertyIdName( final Class<?> entityClass ) {

        String result = null;

        if ( entityClass != null ) {
            result = getPropertyNameAnnoted( entityClass, Id.class );
        }

        return result;
    }

    public static Object getGetMethod( final String property, final Object bean )
            throws NoSuchMethodException, InvocationTargetException {
        Object result = null;

        if ( bean != null && property != null ) {
            final String method = getMethodNameByPropertyName( GET_PREFIX, property );

            final Class<?>[] params = null;
            final Object[] args = null;
            final Class<?> beanClass = bean.getClass();

            // FIXME: Ajustar o catch deste metodo para fazer algo!
            try {
                result = executeMethod( beanClass, bean, method, params,
                        args );
            } catch ( final IllegalArgumentException e ) {
                // Esta excecao e lancada quando um argumento invalido e
                // executado. Para os metodos get nao há argumentos.
            } catch ( final IllegalAccessException e ) {
                //
            }
        }

        return result;
    }

    public static String getMethodNameByPropertyName( final String prefix,
            final String propertyName ) {
        String result = null;

        if ( propertyName != null ) {
            // junta o prefixo +a 1 letra capitalizada o resto da propriedade.
            result = prefix + upperFirst( propertyName );
        }

        return result;
    }

    public static String getPropertyNameAnnoted( final Class<?> beanClass,
            final Class<? extends Annotation> annotationClass ) {
        String property = getPropertyNameAnnotedSearchMethod( beanClass,
                annotationClass );

        if ( property == null ) {
            property = getPropertyNameAnnotedSearchField(
                    beanClass, annotationClass );
        }
        if ( property == null ) {
            throw new IllegalArgumentException( "Propriedade não encontrada por reflection" );
        }

        return property;
    }

    public static String getPropertyNameAnnotedSearchMethod( final Class<?> clazz,
            final Class<? extends Annotation> annotationClass ) {
        String result = null;

        final Method[] lstMethods = clazz.getMethods();

        for ( final Method method : lstMethods ) {
            final String name = method.getName();

            final boolean isBeanMethod = isBeanMethod( name );

            if ( !isBeanMethod )
                continue;

            final boolean isAnnotationPresent = method
                    .isAnnotationPresent( annotationClass );

            if ( isAnnotationPresent ) {
                result = getPropertyName( method.getName() );
                break;
            }
        }

        return result;
    }

    public static String getPropertyNameAnnotedSearchField(
            final Class<?> beanClass,
            final Class<? extends Annotation> annotationClass ) {
        String result = null;

        // pega as referencias principais para o reflection
        final Field[] lstField = beanClass.getDeclaredFields();

        for ( final Field field : lstField )
            if ( field.isAnnotationPresent( annotationClass ) ) {
                final String name = field.getName();

                result = name;
                break;
            }

        final Class<?> superClass = beanClass.getSuperclass();

        if ( result == null ) {
            // se nao achou na classe atual tenta na classe pai.
            if ( superClass != Object.class ) {
                result = getPropertyNameAnnoted( superClass,
                        annotationClass );
            }
        }
        return result;
    }

    public static boolean isBeanMethod( final String methodName ) {
        final boolean result = ( isGetOrIs( methodName ) || isSet( methodName ) );
        return result;
    }

    public static boolean isGetOrIs( final String methodName ) {
        final boolean result = ( isGet( methodName ) || isIs( methodName ) );

        return result;
    }

    public static boolean isGet( final String methodName ) {
        final boolean result = ( methodName.startsWith( GET_PREFIX ) );

        return result;
    }

    public static boolean isSet( final String methodName ) {
        final boolean result = ( methodName.startsWith( SET_PREFIX ) );

        return result;
    }

    public static boolean isIs( final String methodName ) {
        final boolean result = ( methodName.startsWith( IS_PREFIX ) );

        return result;
    }

    public static String getPropertyName( final String methodName ) {

        final String result;

        if ( isIs( methodName ) )
            result = lowerFirst( methodName.substring( 2 ) );
        else
            result = lowerFirst( methodName.substring( 3 ) );

        return result;
    }

    public static String lowerFirst( final String strInput ) {
        String result = "";

        if ( strInput != null && !strInput.isEmpty() ) {
            final Character firstChar = Character.toLowerCase( strInput
                    .charAt( 0 ) );

            result = firstChar + strInput.substring( 1, strInput.length() );
        }

        return result;
    }

    public static String upperFirst( final String strInput ) {
        String resposta = "";

        if ( strInput != null && !strInput.isEmpty() ) {
            // pega a 1 letra da propriedade.
            final Character firstLetter = Character.toUpperCase( strInput
                    .charAt( 0 ) );

            // pega a propriedade sem a 1 letra.
            final String text = strInput.substring( 1 );

            // junta o prefixo +a 1 letra capitalizada +o resto da propriedade.
            resposta = firstLetter + text;
        }

        return resposta;
    }

    public static Object executeMethod( final Class<?> clazz,
            final Object instance, final String methodName,
            final Class<?>[] parametersTypes, final Object[] args )
            throws NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {

        Object result;

        if ( methodName == null ) {
            throw new IllegalArgumentException(
                    "O argumento executeMethod.methodName"
                            + " de ser diferente de null e de vazio." );
        }
        // pega a referencia ao metodo desejado.
        final Method method = clazz.getMethod( methodName, parametersTypes );

        // indica que a acessibilidade sera true.
        method.setAccessible( true );

        // executa o metodo e guarda a resposta dele.
        result = method.invoke( instance, args );

        return result;
    }
}
