package com.stella.assignment2;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiIntegrationTest {
    private static String accessToken;

    @BeforeAll
    public static void setup() {
        String baseUrl = System.getenv("API_BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("API_BASE_URL is not set");
        }
        RestAssured.baseURI = baseUrl;

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "testuser");
        credentials.put("password", "testpassword");

        Response response = given()
                .contentType("application/json")
                .body(credentials)
                .post("/v1/login");

        accessToken = response.jsonPath().getString("access_token");
    }

    @Test
    public void testLoginSuccess() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "testuser");
        credentials.put("password", "testpassword");

        given()
                .contentType("application/json")
                .body(credentials)
                .post("/v1/login")
                .then()
                .statusCode(200)
                .body("access_token", notNullValue());
    }

    @Test
    public void testLoginFailure() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "wrong");
        credentials.put("password", "wrong");

        given()
                .contentType("application/json")
                .body(credentials)
                .post("/v1/login")
                .then()
                .statusCode(401);
    }

    @Test
    public void testHealthCheck() {
        given()
                .get("/v1/healthcheck")
                .then()
                .statusCode(200)
                .body("status", equalTo("OK"));
    }

    @Test
    public void testGetMovieById() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/v1/movie/1")
                .then()
                .statusCode(200)
                .body("movieId", equalTo(1))
                .body("title", notNullValue())
                .body("genres", notNullValue());
    }

    @Test
    public void testGetMovieByQuery() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/v1/movie?id=1")
                .then()
                .statusCode(200)
                .body("movieId", equalTo(1))
                .body("title", notNullValue())
                .body("genres", notNullValue());
    }

    @Test
    public void testMovieNotFound() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/v1/movie/999999")
                .then()
                .statusCode(404)
                .body("error", equalTo("Movie Not Found"));
    }

    @Test
    public void testGetRatings() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/v1/rating/1")
                .then()
                .statusCode(200)
                .body("movieId", equalTo(1))
                .body("average_rating", notNullValue());
                //.body("average_rating", instanceOf(Float.class));
    }

    @Test
    public void testRatingsNotFound() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/v1/rating/999999")
                .then()
                .statusCode(404)
                .body("error", equalTo("No ratings found for this movie"));
    }

    @Test
    public void testGetLink() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/v1/link/1")
                .then()
                .statusCode(200)
                .body("movieId", equalTo(1))
                .body("imdbId", notNullValue())
                .body("tmdbId", notNullValue());
    }

    @Test
    public void testLinkNotFound() {
        given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/v1/link/999999")
                .then()
                .statusCode(404)
                .body("error", equalTo("Movie Not Found"));
    }

    @Test
    public void testUnauthorizedAccess() {
        given()
                .get("/v1/movie/1")
                .then()
                .statusCode(401)
                .body(equalTo(""));
    }
}
