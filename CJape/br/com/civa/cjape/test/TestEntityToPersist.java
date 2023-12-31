import br.com.civa.cjape.CJape;
import br.com.civa.cjape.annotations.EntityField;
import br.com.civa.cjape.annotations.EntityName;
import br.com.civa.cjape.annotations.PrimaryKey;
import br.com.civa.cjape.JapeField;
import br.com.civa.cjape.enums.EntityFieldTypes;
import models.WithEntityName;
import models.WithoutPersistenceAnnotation;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 16:04
 * @LastCommit 20/11/2023 at 17:33:47
 * @Description Validates that the class implements the annotations required for persistence methods.
 */

public class TestEntityToPersist {

    @Test(
            testName = "Create entity using @EntityName.",
            description = "Should create a new entity to persistence."
    )
    public void testcreateEntityWithEntityName() throws Exception {
        WithEntityName withEntityName = new WithEntityName();
        new CJape<>(withEntityName);
    }

    @Test(
            testName = "Create entity without using Persistence Annotation",
            description = "Should fail when creating an entity without persistence annotation",
            expectedExceptions = Exception.class
    )
    public void testErrorWithoutAnnotationsToPersistence() throws Exception {
        WithoutPersistenceAnnotation withoutPersistenceAnnotation = new WithoutPersistenceAnnotation();
        new CJape<>(withoutPersistenceAnnotation);
    }

    @Test(
            testName = "Get Fields from class",
            description = "Should find the fields"
    )
    public void testFindFields() throws Exception {
        WithEntityName withEntityName = new WithEntityName();
        CJape<WithEntityName> japeWithEntityName = new CJape<>(withEntityName);


        JapeField<WithEntityName> entityFieldPk = japeWithEntityName.getField("primaryKeyFieldOnDB".toUpperCase());
        if (!(entityFieldPk.getValueAsBigDecimal().compareTo(BigDecimal.TEN) == 0)) {
            throw new Exception(String.format("%s values not equals 0.", entityFieldPk.getName()));
        }
        if (!entityFieldPk.isPrimaryKey()) {
            throw new Exception(String.format("The field %s is PK.", entityFieldPk.getName()));
        }


        JapeField<WithEntityName> entityFielStr = japeWithEntityName.getField("fieldVarcharWithDefaultName".toUpperCase());
        if (!entityFielStr.getValueAsString().equals("Description")) {
            throw new Exception(String.format("%s values not equals `Description`.", entityFielStr.getName()));
        }
        if (entityFielStr.isPrimaryKey()) {
            throw new Exception(String.format("The field %s is not PK.", entityFielStr.getName()));
        }
    }

}