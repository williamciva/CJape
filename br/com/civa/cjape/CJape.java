package br.com.civa.cjape;

import br.com.civa.cjape.annotations.EntityName;
import br.com.civa.cjape.annotations.JapePersist;
import br.com.civa.cjape.utils.EntityField;
import br.com.civa.cjape.utils.Utils;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 15:48
 * @LastCommit 11/11/2023 at 12:12:26
 * @Description Main Class to strong persistence using the Jape from Sankhya/ModelCore.
 */
public class CJape<T> {

    private static final Logger logger = LoggerFactory.getLogger(CJape.class);

    private final String entityName;
    private final HashMap<String, EntityField<T>> fields = new HashMap<>();
    private final JapeWrapper entityDAO;
    private final Class<T> clazz;

    public CJape(T entity) throws Exception {
        try {
            this.clazz = (Class<T>) entity.getClass();
            if (Utils.hasAnnotation(this.clazz, EntityName.class)) {
                EntityName annotation = Utils.getAnnotation(this.clazz, EntityName.class);
                this.entityName = annotation.value();

            } else {
                throw new Exception(String.format("The class `%s` does not use the annotation `%s`", entity.getClass().getName(), EntityName.class.getName()));
            }

            this.entityDAO = JapeFactory.dao(this.entityName);

            // Set fields and values
            for (Field field : Utils.getAllFields(this.clazz)) {
                if (Utils.hasAnnotation(field.getClass(), EntityName.class)) {
                    this.fields.put(field.getName(), field.get(entity));
                }
            }


        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void insert() throws Exception {

    }

    public void update(T entityUpd) throws Exception {
        if (entityUpd.getClass().equals(clazz)) {

        } else {
            throw new Exception(String.format("The class %s is not compatible with class passed on constructor %s", entityUpd.getClass(), clazz));
        }
    }


}
