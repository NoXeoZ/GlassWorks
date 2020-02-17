package project.schema;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.Schema;

public class SchemaAvro {
    org.apache.avro.Schema avroHttpRequest = SchemaBuilder.record("AvroHttpRequest")
            .namespace("com.baeldung.avro")
            .fields().requiredLong("requestTime")
            .name("employeeNames")
            .type()
            .array()
            .items()
            .stringType()
            .arrayDefault(null)
            .name("active")
            .type()
            .enumeration("Active")
            .symbols("YES","NO")
            .noDefault()
            .endRecord();
}
