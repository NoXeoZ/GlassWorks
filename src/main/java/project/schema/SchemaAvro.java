package project.schema;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.Schema;

public class SchemaAvro {
    public static Schema getSchema(){
        org.apache.avro.Schema avroHttpRequest = SchemaBuilder.record("AvroHttpRequest")
                .namespace("com.baeldung.avro")
                .fields().requiredString("nom").requiredString("prenom").requiredInt("cip").requiredDouble("prix").requiredInt("idPharm")
                .endRecord();
        return avroHttpRequest;
    }
}
