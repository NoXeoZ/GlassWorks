package project;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.ProducerRecord;
import project.entities.Person;

public class FakePeople {

    public static Person/*ProducerRecord<String,String>*/ generate() throws JsonProcessingException {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String state = faker.address().state();
        String birthDate = faker.date().birthday().toString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return new Person(firstName,lastName,state,birthDate);
        //return new ProducerRecord<String,String>("test",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(new Person(firstName,lastName,state,birthDate)));
    }
}
