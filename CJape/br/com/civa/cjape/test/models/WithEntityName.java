package models;

import br.com.civa.cjape.annotations.EntityField;
import br.com.civa.cjape.annotations.EntityName;
import br.com.civa.cjape.annotations.PrimaryKey;

@EntityName("MyEntity")
public class WithEntityName {
    @PrimaryKey
    @EntityField("primaryKeyFieldOnDB")
    int my_primary_key = 10;

    @EntityField
    String fieldVarcharWithDefaultName = "Description";
}

