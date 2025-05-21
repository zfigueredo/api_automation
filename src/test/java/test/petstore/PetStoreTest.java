package test.petstore;

import api.petstore.PetAPI;
import io.restassured.response.Response;
import listeners.ExtentReportExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ExtentReportExtension.class) //Para que los elementos de los reportes se ejecuten en este test
public class PetStoreTest {

    private static final Logger log = LoggerFactory.getLogger(PetStoreTest.class);

    static PetAPI petAPI;

    @BeforeAll
    static void beforeAll() {
        petAPI = new PetAPI();
    }

    @Test
    void uploadPetImage() {

        File file = new File("src/test/resources/files/kitten2.jpg");

        Response response = petAPI.uploadImage(1258,file);
        log.info(response.asString());

        assertEquals(200,response.statusCode(), "Status code is not 200");

        String message = response.jsonPath().getString("message");
        log.info("message: "+message);

        assertTrue(message.contains("kitten2.jpg"),"Message does not contain the image name");

    }

@ParameterizedTest
@CsvSource({"1010, Lula, available",
            "1011, Bella, sold",
                "1012, Cruela, available"})
        void testAddingMultiplePets(int petId, String name, String status) {
        Response response = petAPI.addPet(petId, name, status);

        assertEquals(200, response.statusCode(), "Status code was not 200");
        int currentPetId = response.jsonPath().getInt("id");
        assertEquals(petId, currentPetId, "Current pet id is incorrect");

    String currentName = response.jsonPath().getString("name");
    assertEquals(name, currentName, "Current name is incorrect");

    String currentStatus = response.jsonPath().getString("status");
    assertEquals(status, currentStatus, "Current status is incorrect");

}

    @Test
    void testWillFail() {
        fail("This is to show a test failing in the report");
    }
}
