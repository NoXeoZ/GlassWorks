package project;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import firstTest.FakeData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import project.entities.Drug;
import project.entities.Person;
import project.entities.Pharm;
import project.entities.Sell;

import javax.xml.crypto.Data;
import java.util.Properties;

public class Producer {

    public static void main(String [] args) {
        Properties props=new Properties();

        props.put("bootstrap.servers","localhost:9092");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String,String> producer=new KafkaProducer<String,String>(props);

        DataBaseAnalyzer.ScanData();

        for(int i=0;i<100;i++){
            try {

                Person customer=FakePeople.generate();
                Drug drug= DataBaseAnalyzer.getRandomDrug();
                Pharm pharmacy= DataBaseAnalyzer.getRandomPharm();

                ObjectMapper mapper = new ObjectMapper();
                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                ProducerRecord<String,String> sellRecord = new ProducerRecord<String,String>("test",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(new Sell(customer, drug, pharmacy)));

                producer.send(sellRecord);
                System.out.println(i);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
    }
}
