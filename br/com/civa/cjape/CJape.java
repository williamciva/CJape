package br.com.civa.cjape;

import br.com.civa.cjape.annotations.EntityName;
import br.com.civa.cjape.annotations.JapePersist;
import br.com.civa.cjape.utils.Utils;
import br.com.sankhya.modelcore.MGEModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 15:48
 * @LastCommit dd/mm/yyyy at HH:MM:SS
 * @Description Main Class to strong persistence using the Jape from Sankhya/ModelCore.
 */
public class CJape<T> {

    private static final Logger logger = LoggerFactory.getLogger(CJape.class);

    private final String entityName;
    private final Class<T> clazz;

    public CJape(T entity) throws Exception {
        try {
            this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (Utils.hasAnnotation(entity, JapePersist.class)) {
                this.entityName = Utils.getAnnotation(entity, JapePersist.class).entityName();

            } else if (Utils.hasAnnotation(entity, EntityName.class)) {
                this.entityName = Utils.getAnnotation(entity, EntityName.class).value();

            } else {
                throw new Exception(String.format("The class `%s` does not use the annotation `%s`", entity.getClass().getName(), EntityName.class.getName()));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void insert() {
        logger.info(this.entityName);
    }

    public void update(T entityUpd) throws Exception {
        if (entityUpd.getClass().equals(clazz)) {

        } else {
            throw new Exception(String.format("The class %s is not compatible with class passed on constructor %s", entityUpd.getClass(), clazz));
        }
    }

}
