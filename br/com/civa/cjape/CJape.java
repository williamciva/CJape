package br.com.civa.cjape;

import br.com.civa.cjape.anotations.EntityName;
import br.com.civa.cjape.utils.Utils;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 15:48
 * @LastCommit dd/mm/yyyy at HH:MM:SS
 * @Description Main Class to strong persistence using the Jape from Sankhya/ModelCore.
 */
public class CJape {

    public <T> CJape(T entity) throws Exception {
        try {
            if (Utils.hasAnnotation(entity, EntityName.class)) {

            } else {
                throw new Exception(String.format("Class %s does not use the notation %s", entity.getClass().getName(), EntityName.class.getName()));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
