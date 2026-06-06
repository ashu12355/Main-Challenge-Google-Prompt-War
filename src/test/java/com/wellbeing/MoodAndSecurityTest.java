package com.wellbeing;

import com.wellbeing.dto.AuthResponse;
import com.wellbeing.dto.LoginRequest;
import com.wellbeing.dto.MoodLogRequest;
import com.wellbeing.dto.SignupRequest;
import com.wellbeing.model.MoodType;
import com.wellbeing.model.TargetExam;
import com.wellbeing.model.TriggerTag;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoodAndSecurityTest {

    @LocalServerPort
    private int port;

    private String tokenA;
    private String tokenB;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        // Register and Login User A
        SignupRequest signupA = new SignupRequest("student_a", "password123", TargetExam.JEE);
        given()
                .contentType(ContentType.JSON)
                .body(signupA)
                .when()
                .post("/api/v1/auth/signup");

        AuthResponse authA = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("student_a", "password123"))
                .when()
                .post("/api/v1/auth/login")
                .as(AuthResponse.class);
        tokenA = authA.getToken();

        // Register and Login User B
        SignupRequest signupB = new SignupRequest("student_b", "password123", TargetExam.NEET);
        given()
                .contentType(ContentType.JSON)
                .body(signupB)
                .when()
                .post("/api/v1/auth/signup");

        AuthResponse authB = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("student_b", "password123"))
                .when()
                .post("/api/v1/auth/login")
                .as(AuthResponse.class);
        tokenB = authB.getToken();
    }

    @Test
    public void testMoodLoggingAndSecurityIsolation() {
        // Log mood for User A
        MoodLogRequest moodRequest = new MoodLogRequest(MoodType.ANXIOUS, TriggerTag.MOCK_TEST_PERCENTILE);
        given()
                .header("Authorization", "Bearer " + tokenA)
                .contentType(ContentType.JSON)
                .body(moodRequest)
                .when()
                .post("/api/v1/moods")
                .then()
                .statusCode(200)
                .body("mood", is("ANXIOUS"))
                .body("triggerTag", is("MOCK_TEST_PERCENTILE"));

        // Verify User A's analytics show the log
        given()
                .header("Authorization", "Bearer " + tokenA)
                .when()
                .get("/api/v1/moods/analytics")
                .then()
                .statusCode(200)
                .body("logs", hasSize(1))
                .body("logs[0].mood", is("ANXIOUS"))
                .body("moodCounts.ANXIOUS", is(1));

        // Verify User B's analytics remain isolated (empty)
        given()
                .header("Authorization", "Bearer " + tokenB)
                .when()
                .get("/api/v1/moods/analytics")
                .then()
                .statusCode(200)
                .body("logs", hasSize(0))
                .body("moodCounts.ANXIOUS", is(0));

        // Verify unauthorized requests (no token) are blocked
        given()
                .contentType(ContentType.JSON)
                .body(moodRequest)
                .when()
                .post("/api/v1/moods")
                .then()
                .statusCode(403);

        given()
                .when()
                .get("/api/v1/moods/analytics")
                .then()
                .statusCode(403);
    }
}
