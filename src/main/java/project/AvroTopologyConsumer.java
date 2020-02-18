package project;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import project.entities.Drug;
import project.entities.Sell;
import project.schema.SchemaAvro;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

public class AvroTopologyConsumer {
    public static void main(String [] args) {
            Schema schema = SchemaAvro.getSchema();
            Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);

            Properties props=new Properties();
            props.put(StreamsConfig.APPLICATION_ID_CONFIG, "firstTestd.Consumer");
            props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,Serdes.String().getClass());
            props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.ByteArray().getClass());

            StreamsBuilder builder = new StreamsBuilder();
            KStream<String, byte[]> sourceProcessor = builder.stream("test");

            sourceProcessor.foreach((x, y) -> {
                try {
                    GenericRecord record = recordInjection.invert(y).get();
                    Sell s=new Sell(
                            String.valueOf(record.get("nom")),
                            String.valueOf(record.get("prenom")),
                            Integer.parseInt(String.valueOf(record.get("cip"))),
                            Double.valueOf(String.valueOf(record.get("prix"))),
                            Integer.parseInt(String.valueOf(record.get("idPharm"))));
                    DataBaseAnalyzer.addSellingToDB(s);
                }catch(Exception e){
                    System.out.println("problem occured");
                }
            });

            KafkaStreams kafkaStreams = new KafkaStreams(
                    builder.build(),
                    props);
            kafkaStreams.cleanUp();
            kafkaStreams.start();
    }
}
