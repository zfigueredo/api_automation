package test.booker;

import api.base.APIBase;
import api.booker.BookingAPI;
import io.restassured.response.Response;
import listeners.ExtentReportExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ExtentReportExtension.class) //Para que los elementos de los reportes se ejecuten en este test
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BookingTest {

    private static final Logger log = LoggerFactory.getLogger(BookingTest.class);

    static BookingAPI bookingAPI;
    static int bookingId;

    @BeforeAll
    static void beforeAll() {
        bookingAPI = new BookingAPI();
    }

    @Test
    @Order(2)
    void testGetBooking() {

        Response response = bookingAPI.getBooking(bookingId);

        Assertions.assertNotNull(response);
        assertEquals(200, response.statusCode(), "Status code is not 200");

        String lastName = response.jsonPath().getString("lastname");
        log.info("Booking lastName: "+lastName);
        assertEquals("Perez",lastName, "Booking lastName is not correct");

    }

    @Test
    @Order(3)
    void updateBooking() {
        Booking booking = new Booking("Teresa","Fernandez",150,true,
                "2025-05-19","2025-05-23","Breakfast");

        Response response = bookingAPI.updateBooking(bookingId, booking);
        assertEquals(200, response.statusCode(), "Status code is not 200");
        assertEquals("Fernandez", response.jsonPath().getString("lastname"), "LastName is incorrect");

    }

    @Test
    @Order(4)
    void deleteBooking() {
        Response response = bookingAPI.deleteBooking(bookingId);
        assertEquals(201, response.statusCode(), "Status code is not 201");
    }

    @Test
    @Order(5)
    void verifyDeletedBooking() {
        Response response = bookingAPI.getBooking(bookingId);
        assertEquals(404, response.statusCode(), "Status code is not 404");

    }

    @Test
    @Order(1)
    void createBooking() {
        Booking booking = new Booking("Teresa","Perez",150,true,
                "2025-05-19","2025-05-23","Breakfast");

        Response response = bookingAPI.createBooking(booking);

        assertEquals(200,response.statusCode(),"Status code is not 200");

        bookingId = response.jsonPath().getInt("bookingid");
        log.info("bookingId: "+bookingId);
        assertTrue(bookingId>0,"bookingId should be greater than 0.");

        String firstName = response.jsonPath().getString("booking.firstname");
        log.info("Booking firstName: "+firstName);
        assertEquals("Teresa",firstName, "Booking firstName is not correct");

        String lastName = response.jsonPath().getString("booking.lastname");
        log.info("Booking lastName: "+lastName);
        assertEquals("Perez",lastName, "Booking lastName is not correct");

        int price = response.jsonPath().getInt("booking.totalprice");
        log.info("Booking price: "+price);
        assertEquals(150,price,"Booking price is not correct");

    }
}
