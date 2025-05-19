package test.booker;

import api.base.APIBase;
import api.booker.BookingAPI;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Booking;

public class BookingTest {

    private static final Logger log = LoggerFactory.getLogger(BookingTest.class);

    static BookingAPI bookingAPI;
    static int bookingId = 1;

    @BeforeAll
    static void beforeAll() {
        bookingAPI = new BookingAPI();
    }

    @Test
    void testGetBooking() {

        Response response = bookingAPI.getBooking(bookingId);
        Assertions.assertNotNull(response);

    }
}
