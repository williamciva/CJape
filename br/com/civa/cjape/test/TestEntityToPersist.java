import br.com.civa.cjape.CJape;
import br.com.civa.cjape.annotations.EntityFieldName;
import br.com.civa.cjape.annotations.EntityName;
import br.com.civa.cjape.annotations.PrimaryKey;
import br.com.civa.cjape.utils.EntityField;
import br.com.sankhya.modelcore.MGEModelException;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 16:04
 * @LastCommit 13/11/2023 at 17:16:13
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


        EntityField<WithEntityName> entityFieldPk = japeWithEntityName.getField("primaryKeyFieldOnDB");
        if (!(entityFieldPk.getValueAsBigDecimal().compareTo(BigDecimal.TEN) == 0)) {
            throw new Exception(String.format("%s values not equals 0.", entityFieldPk.getName()));
        }
        if (!entityFieldPk.isPrimaryKey()) {
            throw new Exception(String.format("The field %s is PK.", entityFieldPk.getName()));
        }


        EntityField<WithEntityName> entityFielStr = japeWithEntityName.getField("fieldVarcharWithDefaultName");
        if (!entityFielStr.getValueAsString().equals("Description")) {
            throw new Exception(String.format("%s values not equals `Description`.", entityFielStr.getName()));
        }
        if (entityFielStr.isPrimaryKey()) {
            throw new Exception(String.format("The field %s is not PK.", entityFielStr.getName()));
        }
    }

}


@EntityName("MyEntity")
class WithEntityName {
    @PrimaryKey
    @EntityFieldName("primaryKeyFieldOnDB")
    int my_primary_key = 10;

    @EntityFieldName
    String fieldVarcharWithDefaultName = "Description";
}

class WithoutPersistenceAnnotation {
}