package com.demo.junitmock.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;


public class AssuredTest {



    @Test
    void restAssured() {
//        RestAssured
//                .given()
//                .log().all()
//                .when()
//                .contentType(ContentType.JSON)
////                .body(orderReq)
////                .post("http://localhost:8080/users/save")
//                .then();


    String orderReq = "{\"id\":4," +
            "\"name\":\"ENKANTON NAZARO\"," +
            "\"login\":\"ENKANTON\"," +
            "\"email\":\"ENKANTON@mail.com\"}";



        RestAssured
                .given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(orderReq)
                .post("http://localhost:8080/users/save")
                .then()
                .log().all()
                    .statusCode(200);
    }

}
