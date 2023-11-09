package br.com.civa.cjape;

import br.com.civa.cjape.annotations.EntityName;
import br.com.civa.cjape.annotations.JapePersist;
import br.com.civa.cjape.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 15:48
 * @LastCommit dd/mm/yyyy at HH:MM:SS
 * @Description Main Class to strong persistence using the Jape from Sankhya/ModelCore.
 */
public class CJape {

    private static final Logger logger = LoggerFactory.getLogger(CJape.class);

    private final String entityName;

    public <T> CJape(T entity) throws Exception {
        try {
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

    public void persist() {
        logger.info(this.entityName);
    }

}
