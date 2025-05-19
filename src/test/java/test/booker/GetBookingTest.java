package test.booker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class GetBookingTest {

    @Test
    void getBooking() {

        Response response = given().contentType(ContentType.JSON).when().log().all().get("https://restful-booker.herokuapp.com/booking/1");
        System.out.println(response.asString());

        assertNotNull(response);
        assertEquals(200,response.statusCode(), "Status code was not 200");
    }
}
