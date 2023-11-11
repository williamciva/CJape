package br.com.civa.cjape.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 15:56
 * @LastCommit 11/11/2023 at 12:12:26
 * @Description Required Antotation for persistence.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {
d}
