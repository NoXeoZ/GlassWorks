package project.avroTransaction.simple;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import project.avroTransaction.schema.SchemaAvro;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AvroConsumer {
    public static void main(String [] args) {
        Properties props=new Properties();

        props.put("bootstrap.servers","localhost:9092");
        props.put("group.id","firstTest.Consumer");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.ByteArrayDeserializer");

        KafkaConsumer<String,byte[]> consumer = new KafkaConsumer<String,byte[]>(props);

        consumer.subscribe(Collections.singletonList("test"));

        int noRecordsCount=0;
        int giveUp=1000;

        Schema schema = SchemaAvro.getSchema();
        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);

        while (true) {
            ConsumerRecords<String,byte[]> consumerRecords = consumer.poll(Duration.ofMillis(2000));

            noRecordsCount+=consumerRecords.count();

            consumerRecords.forEach(record -> {
                GenericRecord r = recordInjection.invert(record.value()).get();
                System.out.println(r.get("nom"));
                System.out.println(r.get("prenom"));
                System.out.println(r.get("cip"));
                System.out.println(r.get("prix"));
                System.out.println(r.get("idPharm"));
                /*System.out.printf("firstTest.Consumer Record:(%s, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());*/
            });

            consumer.commitAsync();
        }
        //consumer.close();
        //System.out.println("DONE");
    }
}
