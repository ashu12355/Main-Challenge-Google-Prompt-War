package com.wellbeing;

import com.wellbeing.dto.AuthResponse;
import com.wellbeing.dto.SignupRequest;
import com.wellbeing.model.TargetExam;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NudgeAsyncTest {

    @LocalServerPort
    private int port;

    private String token;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        // Register and login student
        SignupRequest signup = new SignupRequest("async_student", "password123", TargetExam.UPSC);
        given()
                .contentType(ContentType.JSON)
                .body(signup)
                .when()
                .post("/api/v1/auth/signup");

        AuthResponse auth = given()
                .contentType(ContentType.JSON)
                .body(new SignupRequest("async_student", "password123", TargetExam.UPSC))
                .when()
                .post("/api/v1/auth/login")
                .as(AuthResponse.class);
        token = auth.getToken();
    }

    @Test
    public void testPersonalizedNudgeAsyncExecution() {
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/v1/nudges/personalized")
                .then()
                .statusCode(200)
                .body("nudgeText", notNullValue())
                .body("contextTag", is("GENERAL"));
    }
}
