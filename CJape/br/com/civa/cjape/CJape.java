package br.com.civa.cjape;

import br.com.civa.cjape.annotations.EntityField;
import br.com.civa.cjape.annotations.EntityName;
import br.com.civa.cjape.utils.Utils;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static br.com.civa.cjape.utils.Utils.getJapeFieldsFromClass;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 15:48
 * @LastCommit 20/11/2023 at 17:33:47
 * @Description Main Class to strong persistence using the Jape from Sankhya/ModelCore.
 */
public class CJape<T> {

    private static final Logger LOGGER = LogManager.getLogger(CJape.class);

    private final String entityName;
    private HashMap<String, JapeField<T>> fields = new HashMap<>();
    private final JapeWrapper entityDAO;
    private final Class<T> clazz;

    public CJape(T entity) throws Exception {
        try {
            this.clazz = (Class<T>) entity.getClass();
            if (Utils.hasAnnotation(this.clazz, EntityName.class)) {
                EntityName annotation = Utils.getAnnotation(this.clazz, EntityName.class);
                this.entityName = annotation.value();

            } else {
                throw new Exception(String.format("The class `%s` does not use the annotation `%s`",this.clazz.getName(), EntityName.class.getName()));
            }

            this.entityDAO = JapeFactory.dao(this.entityName);


            // Set fields and values
            for (JapeField<T> field : Utils.getJapeFieldsFromClass(entity, this.clazz)) {
                    this.fields.put(field.getName(), field);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public T insert() throws Exception {
        FluidCreateVO entityVO = this.entityDAO.create();

        for (Map.Entry<String, JapeField<T>> entry: this.fields.entrySet()) {
            entityVO.set(entry.getKey(), (entry.getValue()).getValue());
        }

        DynamicVO regVo = entityVO.save();
        return Utils.getInstanceFromVO(regVo, this.clazz);
    }

    public void update(T entityUpd) throws Exception {
        if (entityUpd.getClass().equals(clazz)) {

        } else {
            throw new Exception(String.format("The class %s is not compatible with class passed on constructor %s", entityUpd.getClass(), clazz));
        }
    }

    public HashMap<String, JapeField<T>> getFields() {
        return this.fields;
    }

    public JapeField<T> getField(String field) {
        return this.fields.get(field.toUpperCase());
    }

}
