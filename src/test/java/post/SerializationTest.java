package post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import pojo.PetStorePojo;

import java.io.File;
import java.io.IOException;

public class SerializationTest {

    @Test
    public  void serializationTest() throws IOException {

        PetStorePojo pet = new PetStorePojo();
        pet.setId(3487);
        pet.setName("zeus");
        pet.setStatus("gone fishing");

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("src/test/resources/pet.json");

        objectMapper.writeValue(file,pet);
    }
}
