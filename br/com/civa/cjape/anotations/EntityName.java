package br.com.civa.cjape.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 15:56
 * @LastCommit dd/mm/yyyy at HH:MM:SS
 * @Description Required Antotation for persistence.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityName {

    String value();

}
