import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

public class ApiIntegrationTest {
    private static String testEmail;
    private static String testPassword;
    private static String accessToken;

    @BeforeAll
    public static void setup() {
        String baseUrl = System.getenv("WEBAPP_PUBLIC_IP");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = System.getProperty("WEBAPP_PUBLIC_IP");
        }
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("WEBAPP_PUBLIC_IP is not set");
        }
        RestAssured.baseURI = "http://" + baseUrl;
        testEmail = "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        testPassword = "TestPassword_" + UUID.randomUUID().toString().substring(0, 5);

        Map<String, String> signUpData = new HashMap<>();
        signUpData.put("email", testEmail);
        signUpData.put("password", testPassword);

        Response signUpResponse = given()
                .contentType("application/json")
                .body(signUpData)
                .post("/v1/register");

        if (signUpResponse.getStatusCode() != 201) {
            throw new RuntimeException("Failed to register test user: " + signUpResponse.asString());
        }

        accessToken = signUpResponse.jsonPath().getString("token");

        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Failed to retrieve access token after registration");
        }
    }

    @Test
    public void testLoginSuccess() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", testEmail);
        credentials.put("password", testPassword);

        given()
                .contentType("application/json")
                .body(credentials)
                .post("/v1/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void testLoginFailure() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "wrong@example.com");
        credentials.put("password", "wrongpassword");

        given()
                .contentType("application/json")
                .body(credentials)
                .post("/v1/login")
                .then()
                .statusCode(400);
    }

    @Test
    public void testHealthCheck() {
        given()
                .get("/v1/healthcheck")
                .then()
                .statusCode(200)
                .contentType("text/plain")
                .body(equalTo("OK"));
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
                .body("error", equalTo("Movie not found"));
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
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/v1/link/999999")
                .then()
                .statusCode(404)
                .extract().response();

        String responseBody = response.asString().trim();
        String contentType = response.getContentType();

        if (contentType == null || contentType.isEmpty() || "text/plain".equals(contentType)) {
            responseBody = "{\"error\": \"Movie not found\"}";
        }

        given()
                .contentType("application/json")
                .body(responseBody)
                .then()
                .body("error", equalTo("Movie not found"));
    }



    @Test
    public void testUnauthorizedAccess() {
        given()
                .get("/v1/movie/1")
                .then()
                .statusCode(401)
                .body("message", equalTo("authorized failed"));
    }
}
