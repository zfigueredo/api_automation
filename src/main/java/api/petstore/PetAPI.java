package api.petstore;

import api.base.APIBase;
import api.booker.BookingAPI;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetAPI extends APIBase {

    private static final Logger log = LoggerFactory.getLogger(PetAPI.class);

    private static final String BASE_URL = "https://petstore.swagger.io/v2/";
    private static final String PET_ENDPOINT = "pet";

    public PetAPI() {
        super(BASE_URL);
    }

    public Response addPet(int id, String name, String status){

        Map<String, Object> pet = new HashMap<>();
        pet.put("id", id);
        pet.put("name", name);
        pet.put("status", status);
        pet.put("photoUrls", List.of("https://example.com/"));
        return post(PET_ENDPOINT, pet);
    }

    public Response getPetById(int id){
        return get(PET_ENDPOINT+"/"+id);
    }

    public Response deletePet(int id){
        return delete(PET_ENDPOINT+"/"+id, "");

    }

    public Response uploadImage(int petId, File image){
        String uploadEndpoint = PET_ENDPOINT+"/"+petId+"uploadImage";
        return uploadFile(uploadEndpoint,image);

    }

    public Response waitForPet(int petId, int maxRetries, int delayMilliseconds) throws InterruptedException {

        for (int i = 0; i< maxRetries; i++){
            Response response = getPetById(petId);

            if (response.statusCode() ==200){
                return response;

            }

            log.info("Attempt "+i+" pet not found. Retrying in " +delayMilliseconds + "ms");
            Thread.sleep(delayMilliseconds);
        }

        throw new RuntimeException("Pet with Id: "+petId+" not found" +maxRetries+" retries");




    }






}
