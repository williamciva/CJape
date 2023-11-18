package br.com.civa.cjape.annotations;

import br.com.civa.cjape.enums.EntityFieldTypes;

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
@Target(ElementType.FIELD)
public @interface EntityField {
    String value() default "";
    EntityFieldTypes type() default EntityFieldTypes.UNDEFINED;

}
