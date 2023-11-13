package br.com.civa.cjape.utils;

import br.com.civa.cjape.annotations.EntityFieldName;
import br.com.civa.cjape.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @author William Civa
 * @version 1.0
 * @Created 11/11/2023 at 11:01
 * @LastCommit 13/11/2023 at 17:16:13
 * @Description description here.
 */
public class EntityField<T> {

    private java.lang.reflect.Field field;
    private String name;
    private Object value;
    private boolean isPrimaryKey;

    public EntityField(T instance, Field field) throws IllegalAccessException {
        field.setAccessible(true);

        this.field = field;
        this.value = field.get(instance);
        this.isPrimaryKey = Utils.hasAnnotation(field, PrimaryKey.class);


        this.name = Utils.getAnnotation(field, EntityFieldName.class).value();
        this.name = !this.name.isEmpty() ? this.name : field.getName();
    }

    public java.lang.reflect.Field getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public BigDecimal getValueAsBigDecimal() {
        if (this.value instanceof Number) {
            return BigDecimal.valueOf(((Number) this.value).doubleValue());
        }

        if (this.value instanceof String) {
            return new BigDecimal((String) this.value);
        }

        return null;
    }

    public String getValueAsString() {
        if (this.value instanceof String) {
            return (String) this.value;
        }

        return null;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }
}
