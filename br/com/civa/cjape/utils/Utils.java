package br.com.civa.cjape.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrador
 * @version 1.0
 * @Created 08/11/2023 at 17:07
 * @LastCommit 11/11/2023 at 12:12:26
 */
public final class Utils {

    public static <T> boolean hasAnnotation(Class<T> instance, Class<? extends Annotation> annotationClass) {
        Class<?> clazz = instance;
        Annotation annotation = clazz.getAnnotation(annotationClass);
        return annotation != null;
    }

    public static <T, A extends Annotation> A getAnnotation(Class<T> clazz, Class<A> annotationClass) {
        return clazz.getAnnotation(annotationClass);
    }

    public static <T> Field[] getAllFields(Class<T> clazz) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        // Obter campos das superclasses recursivamente
        Class<?> superclasse = clazz.getSuperclass();
        while (superclasse != null) {
            fields.addAll(Arrays.asList(superclasse.getDeclaredFields()));
            superclasse = superclasse.getSuperclass();
        }

        return fields.toArray(new Field[0]);
    }

}