package project.avroTransaction.schema;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.Schema;

public class SchemaAvro {
    public static Schema getSchema(){
        org.apache.avro.Schema avroHttpRequest = SchemaBuilder.record("AvroSell")
                .namespace("com.baeldung.avro")
                .fields().requiredString("nom").requiredString("prenom").requiredInt("cip").requiredDouble("prix").requiredInt("idPharm")
                .endRecord();
        return avroHttpRequest;
    }

    public static Schema getAnonSchema(){
        org.apache.avro.Schema avroHttpRequest = SchemaBuilder.record("AvroAnonSell")
                .namespace("com.baeldung.avro")
                .fields().requiredInt("cip").requiredDouble("prix").requiredInt("idPharm")
                .endRecord();
        return avroHttpRequest;
    }

    public static Schema getPharmSchema(){
        org.apache.avro.Schema avroHttpRequest = SchemaBuilder.record("AvroPharm")
                .namespace("com.baeldung.avro")
                .fields().requiredInt("id").requiredString("nom").requiredString("address").requiredString("depart").requiredString("region")
                .endRecord();
        return avroHttpRequest;
    }

    public static Schema getMergedSchema(){
        org.apache.avro.Schema avroHttpRequest = SchemaBuilder.record("AvroAnonSell")
                .namespace("com.baeldung.avro")
                .fields().requiredInt("cip").requiredDouble("prix").requiredInt("idPharm").requiredString("pharmRegion")
                .endRecord();
        return avroHttpRequest;
    }
}
