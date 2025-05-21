package api.booker;

import api.base.APIBase;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Booking;

import java.util.HashMap;
import java.util.Map;

public class BookingAPI extends APIBase {


    private static final Logger log = LoggerFactory.getLogger(BookingAPI.class);

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";
    private static final String AUTH_ENDPOINT = "/auth";
    private static final String BOOKING_ENDPOINT = "/booking";
    private String token;



    //Constructor de la clase
    public BookingAPI() {
        super(BASE_URL);
        token = getToken();
    }

    public static String getEnvVar(String key){
        String value = System.getenv(key);
        if(value == null || value.isEmpty()){
            throw new RuntimeException("Env variable '" + key + "'is not set");
        }
        return value;

    }

    private String getUsername(){
        return getEnvVar("BOOKER_USER");
    }

    private String getPassword(){
        return getEnvVar("BOOKER_PASSWORD");
    }

    public String getToken(){

        Map<String, Object> body = new HashMap<>();
        body.put("username", getUsername());
        body.put("password", getPassword());

        Response response = postNo_log(AUTH_ENDPOINT, body);
        return response.jsonPath().getString("token");

    }

    public Response getBooking(int bookingId){
        return get(BOOKING_ENDPOINT+"/"+bookingId);

    }

    public Response createBooking(Booking booking){

        Map<String,Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", booking.getCheckin());
        bookingDates.put("checkout", booking.getCheckout());

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", booking.getFirstname());
        body.put("lastname", booking.getLastname());
        body.put("totalprice", booking.getTotalprice());
        body.put("depositpaid", booking.isDepositpaid());
        body.put("bookingdates",bookingDates);
        body.put("additionalneeds", booking.getAdditionalneeds());

        return post(BOOKING_ENDPOINT, body);

    }


    public Response updateBooking(int bookingId,Booking booking){
        Map<String,Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", booking.getCheckin());
        bookingDates.put("checkout", booking.getCheckout());

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", booking.getFirstname());
        body.put("lastname", booking.getLastname());
        body.put("totalprice", booking.getTotalprice());
        body.put("depositpaid", booking.isDepositpaid());
        body.put("bookingdates",bookingDates);
        body.put("additionalneeds", booking.getAdditionalneeds());

        return put(BOOKING_ENDPOINT+"/"+bookingId,body,token);

    }
    public Response updateBooking(int bookingId,Booking booking, String _token){

        Map<String,Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", booking.getCheckin());
        bookingDates.put("checkout", booking.getCheckout());

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", booking.getFirstname());
        body.put("lastname", booking.getLastname());
        body.put("totalprice", booking.getTotalprice());
        body.put("depositpaid", booking.isDepositpaid());
        body.put("bookingdates",bookingDates);
        body.put("additionalneeds", booking.getAdditionalneeds());

        return put(BOOKING_ENDPOINT+"/"+bookingId,body,_token);

    }


    public Response deleteBooking(int bookingId){

        return delete(BOOKING_ENDPOINT+"/"+bookingId,token);
    }



}
