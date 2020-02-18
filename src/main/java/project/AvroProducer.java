package project;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import project.entities.Drug;
import project.entities.Person;
import project.entities.Pharm;
import project.entities.Sell;
import project.schema.SchemaAvro;

import java.util.Properties;

public class AvroProducer {

    public static void main(String [] args) {
        Properties props=new Properties();

        props.put("bootstrap.servers","localhost:9092");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");

        KafkaProducer<String, byte[]> producer=new KafkaProducer<String,byte[]>(props);
        Schema schema = SchemaAvro.getSchema();

        DataBaseAnalyzer.ScanData();

        for(int i=0;i<100;i++){
            try {

                Person customer=FakePeople.generate();
                Drug drug= DataBaseAnalyzer.getRandomDrug();
                Pharm pharmacy= DataBaseAnalyzer.getRandomPharm();

                ObjectMapper mapper = new ObjectMapper();
                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

                Sell s=new Sell(customer, drug, pharmacy);

                GenericRecord record = new GenericData.Record(schema);

                record.put("nom",s.getNom());
                record.put("prenom",s.getPrenom());
                record.put("cip",s.getCip());
                record.put("prix",s.getPrix());
                record.put("idPharm",s.getIdPharm());

                Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);
                byte[] bytes=recordInjection.apply(record);

                ProducerRecord<String,byte[]> sellRecord = new ProducerRecord<String, byte[]>("test", bytes);
                producer.send(sellRecord);
                System.out.println(i);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
    }
}
