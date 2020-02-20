package project.avroTransaction.simple;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TopicCreator {
    public static void createTopic(){
        Properties properties = new Properties();

        properties.put("bootstrap.servers","localhost:9092");

        AdminClient adminClient = AdminClient.create(properties);
        NewTopic newTopic = new NewTopic("testanon", 1, (short)1); //new NewTopic(topicName, numPartitions, replicationFactor)

        List<NewTopic> newTopics = new ArrayList<NewTopic>();
        newTopics.add(newTopic);

        adminClient.createTopics(newTopics);
        adminClient.close();
    }
}
