package br.com.civa.cjape.utils;

import java.lang.annotation.Annotation;

/**
 * @author Administrador
 * @version 1.0
 * @Created 08/11/2023 at 17:07
 * @LastCommit dd/mm/yyyy at HH:MM:SS
 */
public final class Utils {

    public static <T> boolean hasAnnotation(T instance, Class<? extends Annotation> annotationClass) {
        Class<?> clazz = instance.getClass();
        Annotation annotation = clazz.getAnnotation(annotationClass);
        return annotation != null;
    }

}