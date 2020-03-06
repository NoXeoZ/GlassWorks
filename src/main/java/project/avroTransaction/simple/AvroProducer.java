package project.avroTransaction.simple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import project.db.DataBaseAnalyzer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import project.entities.*;
import project.avroTransaction.schema.SchemaAvro;

import java.util.Properties;

public class AvroProducer {

    public static void main(String [] args) {
        Properties props=new Properties();

        props.put("bootstrap.servers","localhost:9092");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");

        KafkaProducer<String, byte[]> producer=new KafkaProducer<String,byte[]>(props);
        Schema schema = SchemaAvro.getSchema();
        GenericRecord record = new GenericData.Record(schema);
        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);

        DataBaseAnalyzer.ScanData();

        for(int i=0;i<2;i++){
            try {

                Person customer= FakePeople.generate();
                Drug drug= DataBaseAnalyzer.getRandomDrug();
                Pharm pharmacy= DataBaseAnalyzer.getRandomPharm();

                //ObjectMapper mapper = new ObjectMapper();
                //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

                Sell s=new Sell(customer, drug, pharmacy);

                record.put("nom",s.getNom());
                record.put("prenom",s.getPrenom());
                record.put("cip",s.getCip());
                record.put("prix",s.getPrix());
                record.put("idPharm",s.getIdPharm());

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
