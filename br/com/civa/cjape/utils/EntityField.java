package br.com.civa.cjape.utils;

import br.com.civa.cjape.annotations.PrimaryKey;

import java.lang.reflect.Field;

/**
 * @author William Civa
 * @version 1.0
 * @Created 11/11/2023 at 11:01
 * @LastCommit 11/11/2023 at 12:12:26
 * @Description description here.
 */
public class EntityField<T> {

    private Field field;
    private String name;
    private Object value;
    private boolean primaryKey;

    public EntityField(T instance, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Class<T> clazz = (Class<T>) field.getClass();

        this.field = field;
        this.value = field.get(instance);
        this.primaryKey = Utils.hasAnnotation(clazz, PrimaryKey.class);


        this.name = Utils.getAnnotation(clazz, br.com.civa.cjape.annotations.EntityField.class).value();
        this.name = !this.name.isEmpty() ? this.name : field.getName();
    }

}
