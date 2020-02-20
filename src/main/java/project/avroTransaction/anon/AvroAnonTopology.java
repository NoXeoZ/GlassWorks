package project.avroTransaction.anon;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import project.avroTransaction.simple.TopicCreator;
import project.entities.Sell;
import project.avroTransaction.schema.SchemaAvro;

import java.util.Properties;

public class AvroAnonTopology {
    public static void main(String [] args) {
        Schema schema = SchemaAvro.getSchema();
        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);

        TopicCreator.createTopic();

        Properties props=new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "firstTestd.Consumer");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.ByteArray().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, byte[]> sourceProcessor = builder.stream("test");


        Properties props1=new Properties();

        props1.put("bootstrap.servers","localhost:9092");
        props1.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props1.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");
        Schema anonSchema=SchemaAvro.getAnonSchema();
        GenericRecord prod = new GenericData.Record(anonSchema);
        Injection<GenericRecord, byte[]> prodInjection = GenericAvroCodecs.toBinary(anonSchema);

        KafkaProducer<String, byte[]> producer=new KafkaProducer<String,byte[]>(props1);
        sourceProcessor.foreach((x, y) -> {
            try {
                GenericRecord record = recordInjection.invert(y).get();
                Sell s=new Sell(
                        String.valueOf(record.get("nom")),
                        String.valueOf(record.get("prenom")),
                        Integer.parseInt(String.valueOf(record.get("cip"))),
                        Double.valueOf(String.valueOf(record.get("prix"))),
                        Integer.parseInt(String.valueOf(record.get("idPharm"))));

                prod.put("cip",s.getCip());
                prod.put("prix",s.getPrix());
                prod.put("idPharm",s.getIdPharm());
                byte[] bytes=prodInjection.apply(prod);

                ProducerRecord<String,byte[]> sellRecord = new ProducerRecord<String, byte[]>("testanon", bytes);
                producer.send(sellRecord);

                /*ObjectMapper mapper = new ObjectMapper();
                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                ProducerRecord<String,String> sellRecord = new ProducerRecord<String,String>("testanon",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s));
                */
                //producer.send(sellRecord);

                System.out.println(s.getCip());
                System.out.println(s.getPrix());
                System.out.println(s.getIdPharm());
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
