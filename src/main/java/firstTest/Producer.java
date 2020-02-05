package firstTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class Producer {

    public static void main(String [] args) {
        Properties props=new Properties();

        props.put("bootstrap.servers","localhost:9092");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String,String> producer=new KafkaProducer<String,String>(props);
        for(int i=0;i<100;i++){
            try {
                producer.send(FakeData.generate());
                System.out.println(i);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
    }
}
