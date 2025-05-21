package test.booker;

import api.booker.BookingAPI;
import io.restassured.response.Response;
import listeners.ExtentReportExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Booking;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ExtentReportExtension.class) //Para que los elementos de los reportes se ejecuten en este test
public class bookingNegativesTest {


    private static final Logger log = LoggerFactory.getLogger(bookingNegativesTest.class);

    static BookingAPI bookingAPI;


    @BeforeAll
    static void beforeAll() {
        bookingAPI = new BookingAPI();
    }

    @Test
    void createBookingWithMissingEmptyValue() {
        Booking booking = new Booking(null, null, 0, false, "", "", "");
        Response response = bookingAPI.createBooking(booking);
        assertEquals(500, response.statusCode(), "The status code should be 500");
    }

    @Test
    void updateBookingWithInvalidToken() {
        Booking booking = new Booking("Teresa","Perez",150,true,
                "2025-05-19","2025-05-23","Breakfast");
        Response response_createBooking = bookingAPI.createBooking(booking);
        assertEquals(200, response_createBooking.statusCode(), "Status code is not 200");

        int bookingId = response_createBooking.jsonPath().getInt("bookingid");
        log.info("created bookingId: "+bookingId);

        Response response = bookingAPI.updateBooking(bookingId, booking, "fake token");
        assertEquals(403,response.statusCode(), "Status code is not 403");

    }

    @Test
    void deleteNonExistingBooking() {

        Response response = bookingAPI.deleteBooking(9999999);
        assertEquals(405, response.statusCode(), "The status code should be 405");
    }
}
