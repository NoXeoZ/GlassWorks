package firstTest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.ProducerRecord;

public class FakeData {

    public static ProducerRecord<String,String> generate() throws JsonProcessingException {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String state = faker.address().state();
        String birthDate = faker.date().birthday().toString();
        class Person{
            private String firstName;
            private String lastName;
            private String state;
            private String birthDate;
            Person(String firstName, String lastName, String state, String birthDate) {
                this.firstName=firstName;
                this.lastName=lastName;
                this.state=state;
                this.birthDate=birthDate;
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return new ProducerRecord<String,String>("test",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(new Person(firstName,lastName,state,birthDate)));
    }
}
