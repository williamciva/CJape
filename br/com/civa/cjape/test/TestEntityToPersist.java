import br.com.civa.cjape.CJape;
import br.com.civa.cjape.annotations.EntityName;
import org.testng.annotations.Test;

/**
 * @author William Civa
 * @version 1.0
 * @Created 08/11/2023 at 16:04
 * @LastCommit dd/mm/yyyy at HH:MM:SS
 * @Description Validates that the class implements the annotations required for persistence methods.
 */

public class TestEntityToPersist {

    @Test(
            testName = "Create entity using @EntityName.",
            description = "Should create a new entity to persistence."
    )
    public void createEntityWithEntityName() throws Exception {
        WithEntityName withEntityName = new WithEntityName();
        new CJape(withEntityName);
    }

    @Test(
            testName = "Create entity without using Persistence Annotation",
            description = "Should fail when creating an entity without persistence annotation",
            expectedExceptions = Exception.class
    )
    public void testErrorWithoutAnnotationsToPersistence() throws Exception {
        WithoutPersistenceAnnotation withoutPersistenceAnnotation = new WithoutPersistenceAnnotation();
        new CJape(withoutPersistenceAnnotation);
    }

}


@EntityName("MyEntity")
class WithEntityName {
}

class WithoutPersistenceAnnotation {
}