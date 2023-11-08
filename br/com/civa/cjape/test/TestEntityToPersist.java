import br.com.civa.cjape.CJape;
import br.com.civa.cjape.anotations.EntityName;

/**
 * @author Administrador
 * @version 1.0
 * @Created 08/11/2023 at 16:04
 * @LastCommit dd/mm/yyyy at HH:MM:SS
 * @Description Validates that the class implements the annotations required for persistence methods.
 */

public class TestEntityToPersist {

    MyClassToPersist myClassToPersist = new MyClassToPersist();

    CJape cjape = new CJape(myClassToPersist);

    public TestEntityToPersist() throws Exception {
    }
}


@EntityName("NomeEntidade")
class MyClassToPersist {

}