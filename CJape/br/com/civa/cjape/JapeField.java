package br.com.civa.cjape;

import br.com.civa.cjape.annotations.EntityField;
import br.com.civa.cjape.annotations.PrimaryKey;
import br.com.civa.cjape.enums.EntityFieldTypes;
import br.com.civa.cjape.utils.Utils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author William Civa
 * @version 1.0
 * @Created 11/11/2023 at 11:01
 * @LastCommit 20/11/2023 at 17:33:47
 * @Description description here.
 */
public class JapeField<T> {

    private static final Logger LOGGER = LogManager.getLogger(JapeField.class);
    private static final String ERRRO_CAST_MESSAGE = "Error when converting %s to %s";

    private java.lang.reflect.Field field;
    private String name;
    private Object value;
    private boolean isPrimaryKey;

    public JapeField(T instance, Field field) throws IllegalAccessException {
        field.setAccessible(true);

        this.field = field;
        this.value = instance != null ? field.get(instance) : null;
        this.isPrimaryKey = Utils.hasAnnotation(field, PrimaryKey.class);


        this.name = (Utils.getAnnotation(field, EntityField.class).value()).toUpperCase();
        if (this.name.isEmpty()) {
            this.name = field.getName().toUpperCase();
        }
    }


    public java.lang.reflect.Field getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public Object getDefaultValue() {
        return this.value;
    }

    public Object getValue() throws Exception {
        EntityFieldTypes type = Utils.getAnnotation(field, EntityField.class).type();

        if (!type.equals(EntityFieldTypes.UNDEFINED)) {
            try {

                if (type.equals(EntityFieldTypes.VARCHAR)) return (String) this.value;
                if (type.equals(EntityFieldTypes.CLOB)) return ((String) this.value).toCharArray();

                if (type.equals(EntityFieldTypes.BLOB)) {
                    byte[] tempByte = this.getValueAsByteArray();

                    if (tempByte != null) return tempByte;
                    throw new Exception();
                }

                if (type.equals(EntityFieldTypes.INT)) {
                    BigDecimal intNumber = new BigDecimal(this.value.toString());
                    return new BigDecimal(intNumber.intValue());
                }

                if (type.equals(EntityFieldTypes.FLOAT)) {
                    BigDecimal intNumber = new BigDecimal(this.value.toString());
                    return BigDecimal.valueOf(intNumber.doubleValue());
                }


            } catch (Exception e) {
                throw new Exception(String.format("Impossible to convert %s to \"%s\"", this.field.getType().getName(), type));
            }

        }

        if (this.value instanceof Number) {
            BigDecimal tempNumber = getValueAsBigDecimal();
            if (tempNumber != null) return tempNumber;
        }

        return this.value;
    }

    public BigDecimal getValueAsBigDecimal() {
        if (this.value instanceof Number || this.value instanceof String) {
            BigDecimal tempNumber = new BigDecimal(this.value.toString());

            if (tempNumber.compareTo(BigDecimal.valueOf(tempNumber.intValue())) == 0) {
                return new BigDecimal(tempNumber.intValue());
            } else {
                return tempNumber;
            }
        }

        return null;
    }

    public String getValueAsString() {
        if (this.value instanceof String) {
            return (String) this.value;
        }

        return null;
    }

    public char[] getValueAsCharArray() {
        if (this.value instanceof char[]) {
            return (char[]) this.value;
        }

        if (this.value instanceof String) {
            return ((String) this.value).toCharArray();
        }

        return null;
    }

    public byte[] getValueAsByteArray() {
        if (this.value instanceof char[]) {
            return (byte[]) this.value;
        }

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this.value);
            oos.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            LOGGER.error(String.format(ERRRO_CAST_MESSAGE, this.value.getClass().getName(), "Array Bytes"));
            return null;
        }
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }
}
