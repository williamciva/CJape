package br.com.civa.cjape.utils;

import br.com.civa.cjape.JapeField;
import br.com.civa.cjape.annotations.EntityField;
import br.com.sankhya.jape.vo.DynamicVO;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Administrador
 * @version 1.0
 * @Created 08/11/2023 at 17:07
 * @LastCommit 20/11/2023 at 17:33:47
 */
public final class Utils {

    public static <T> boolean hasAnnotation(Class<T> clazz, Class<? extends Annotation> annotationClass) {
        Annotation annotation = clazz.getAnnotation(annotationClass);
        return annotation != null;
    }

    public static <T> boolean hasAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        Annotation annotation = field.getAnnotation(annotationClass);
        return annotation != null;
    }

    public static <T, A extends Annotation> A getAnnotation(Class<T> clazz, Class<A> annotationClass) {
        return clazz.getAnnotation(annotationClass);
    }

    public static <A extends Annotation> A getAnnotation(Field field, Class<A> annotationClass) {
        return field.getAnnotation(annotationClass);
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

    public static <T> JapeField<T>[] getJapeFieldsFromClass(Class<T> clazz) throws IllegalAccessException {
        return Utils.getJapeFieldsFromClass(null, clazz);
    }

    public static <T> JapeField<T>[] getJapeFieldsFromClass(T instance, Class<T> clazz) throws IllegalAccessException {
        List<JapeField<T>> fields = new ArrayList<>();

        for (Field field : Utils.getAllFields(clazz)) {
            if (Utils.hasAnnotation(field, EntityField.class)) {
                JapeField<T> japeField = new JapeField<>(instance, field);

                fields.add(japeField);
            }
        }

        JapeField<T>[] array = new JapeField[fields.size()];
        return fields.toArray(array);
    }

    public static <T> T getInstanceFromVO(DynamicVO vo, Class<T> clazz) throws Exception {
        Constructor<T> constructor = null;
        Constructor<T>[] constructors = (Constructor<T>[]) clazz.getDeclaredConstructors();

        for (Constructor<T> constructorI : constructors) {
            if (constructorI.getParameterCount() == 0 && Modifier.isPublic(constructorI.getModifiers())) {
                constructor = constructorI;
                break;
            }
        }

        if (constructor == null) {
            throw new Exception(String.format("The class %s is invalid because it does not have a public constructor that does not take even one argument.", clazz.getName()));
        }

        T instance = constructor.newInstance();

        for (Field field : Utils.getAllFields(clazz)) {
            if (Utils.hasAnnotation(field, EntityField.class)) {
                field.setAccessible(true);

                Class<?> clazzField = field.getClass();
                if (clazzField.isAssignableFrom(String.class)) {
                    field.set(instance, vo.asString(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(Integer.class)) {
                    field.set(instance, vo.asInt(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(BigDecimal.class)) {
                    field.set(instance, vo.asBigDecimal(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(Boolean.class)) {
                    field.set(instance, vo.asBoolean(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(char[].class)) {
                    field.set(instance, vo.asClob(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(Collection.class)) {
                    field.set(instance, vo.asCollection(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(Double.class)) {
                    field.set(instance, vo.asDouble(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(byte[].class)) {
                    field.set(instance, vo.asBlob(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(long.class)) {
                    field.set(instance, vo.asLong(field.getName()));
                    continue;
                }
                if (clazzField.isAssignableFrom(Timestamp.class))
                    field.set(instance, vo.asTimestamp(field.getName()));
                else {
                    throw new Exception(String.format("It is impossible to convert the VO to an instance of %s", clazz.getName()));
                }
                ;
            }
        }

        return instance;

    }


}