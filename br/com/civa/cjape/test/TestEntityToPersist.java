import br.com.civa.cjape.CJape;
import br.com.civa.cjape.annotations.EntityName;
import br.com.civa.cjape.annotations.JapePersist;
import org.testng.annotations.Test;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 16:04
 * @LastCommit 11/11/2023 at 12:12:26
 * @Description Validates that the class implements the annotations required for persistence methods.
 */

public class TestEntityToPersist {

    private CJape<WithEntityName> japeWithEntityName;

    @Test(
            testName = "Create entity using @EntityName.",
            description = "Should create a new entity to persistence."
    )
    public void createEntityWithEntityName() throws Exception {
        WithEntityName withEntityName = new WithEntityName();
        this.japeWithEntityName = new CJape<>(withEntityName);
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
            testName = "Run insert",
            description = "Should insert new record."
    )
    public void testInsert() throws Exception {
        this.japeWithEntityName.insert();
    }

    @Test(
            testName = "Run update",
            description = "Should update old record."
    )
    public void testUpdate() throws Exception {
        WithEntityName withEntityName = new WithEntityName();
        this.japeWithEntityName.update(withEntityName);
    }

}


@EntityName("MyEntity")
class WithEntityName {
}

class WithoutPersistenceAnnotation {
}