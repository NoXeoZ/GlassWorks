package project.simpleTransaction;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {

    public static void main(String [] args) {
        Properties props=new Properties();

        props.put("bootstrap.servers","localhost:9092");
        props.put("group.id","firstTest.Consumer");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(props);

        consumer.subscribe(Collections.singletonList("test"));

        int noRecordsCount=0;
        int giveUp=1000;

        while (true) {
            ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofMillis(2000));

            noRecordsCount+=consumerRecords.count();

            consumerRecords.forEach(record -> {
                System.out.printf("firstTest.Consumer Record:(%s, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());
            });

            consumer.commitAsync();
        }
        //consumer.close();
        //System.out.println("DONE");
    }
}
