package com.juaracoding;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestAPI {
    String baseUrl = "https://api.themoviedb.org";
    String tokenAPI = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NzFhNmU2NmNjZmNhZDc4MzUxYmJlYzg4MDM4NzkxOCIsInN1YiI6IjY0ZGI3MGQ2Yjc3ZDRiMTE0MDE4NmFiOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lpW4MEZY3QFKgJITc29lGoce5vUPtSJZ9HFIfsyj3cM";
    String endPoint(String endPoint) {
        return baseUrl + endPoint;
    }

    @Test
    public void getNowPlaying(){
        given().header("Authorization", tokenAPI)
                .queryParam("language", "en-Us")
                .queryParam("page", "1")
                .get(endPoint("/3/movie/now_playing"))
                .then()
                .statusCode(200)
                .body("results.id[0]", equalTo(976573));
    }

    @Test
    public void getMoviePopular(){
        given().header("Authorization", tokenAPI)
                .queryParam("language", "en-Us")
                .queryParam("page", "1")
                .get(endPoint("/3/movie/popular"))
                .then()
                .statusCode(200)
                .body("results.id[0]", equalTo(569094));
    }

    @Test
    public void postMovieRating(){
        JSONObject request = new JSONObject();
        request.put("value", "8.50");
        System.out.println(request.toJSONString());

        given().header("Authorization", tokenAPI)
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post(endPoint("/3/movie/678512/rating"))
                .then()
                .statusCode(201)
                .body("status_message", equalTo("The item/record was updated successfully."))
                .log().all();
    }
}