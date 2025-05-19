package api.base;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static io.restassured.RestAssured.*;

public class APIBase {

    private static final Logger log = LoggerFactory.getLogger(APIBase.class);

    protected String baseUrl;

    public APIBase(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.useRelaxedHTTPSValidation();
    }

    public Response get(String endpoint){
        Response response = given().contentType(ContentType.JSON).when().log().all().get(baseUrl+endpoint);
        log.info("response: "+response.asString());
        return response;
    }

    public Response postNo_log(String endpoint, Object body){

        Response response = given().contentType(ContentType.JSON).body(body).when().post(baseUrl+endpoint);
        return response;
    }

    public Response post(String endpoint, Object body){

        Response response = given().contentType(ContentType.JSON).body(body).when().log().all().post(baseUrl+endpoint);
        return response;
    }

    public Response put(String endpoint, Object body, String token ){

        Response response = given().contentType(ContentType.JSON).cookie("token", token).body(body).when().log().all().put(baseUrl+endpoint);
        return response;

    }

    public Response delete(String endpoint, String token ){

        Response response = given().contentType(ContentType.JSON).cookie("token", token).when().log().all().delete(baseUrl+endpoint);
        return response;

    }



}
