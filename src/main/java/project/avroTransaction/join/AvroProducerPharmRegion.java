package project.avroTransaction.join;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import project.avroTransaction.schema.SchemaAvro;
import project.avroTransaction.topics.TopicCreator;
import project.db.DataBaseAnalyzer;
import project.entities.*;

import java.util.Properties;

public class AvroProducerPharmRegion {

    public static void main(String [] args) {
        Properties props=new Properties();

        props.put("bootstrap.servers","localhost:9092");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");

        TopicCreator.createTopicPharm();

        KafkaProducer<String, byte[]> producer=new KafkaProducer<String,byte[]>(props);
        Schema schema = SchemaAvro.getPharmSchema();
        GenericRecord record = new GenericData.Record(schema);
        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);

        DataBaseAnalyzer.ScanData();

        for(int i=0;i<DataBaseAnalyzer.pharms.size();i++){
                Pharm pharmacy= DataBaseAnalyzer.getRandomPharm();

                //ObjectMapper mapper = new ObjectMapper();
                //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                record.put("id",pharmacy.getId());
                record.put("nom",pharmacy.getNom());
                record.put("address",pharmacy.getAdresse());
                record.put("depart",pharmacy.getDepart());
                record.put("region",pharmacy.getRegion());

                byte[] bytes=recordInjection.apply(record);

                ProducerRecord<String,byte[]> sellRecord = new ProducerRecord<String, byte[]>("testpharm", bytes);
                producer.send(sellRecord);
                System.out.println(i);
        }
    }
}