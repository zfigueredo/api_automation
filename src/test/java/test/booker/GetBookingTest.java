package test.booker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import listeners.ExtentReportExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.*;

@ExtendWith(ExtentReportExtension.class) //Para que los elementos de los reportes se ejecuten en este test
public class GetBookingTest {

    @Test
    void getBooking() {

        Response response = given().contentType(ContentType.JSON).when().log().all().get("https://restful-booker.herokuapp.com/booking/1");
        System.out.println(response.asString());

        assertNotNull(response);
        assertEquals(200,response.statusCode(), "Status code was not 200");
    }
}
