package com.wellbeing;

import com.wellbeing.dto.AuthResponse;
import com.wellbeing.dto.JournalRequest;
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
public class JournalSanitizationTest {

    @LocalServerPort
    private int port;

    private String token;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        // Register and login a student
        SignupRequest signup = new SignupRequest("sanitizer_student", "password123", TargetExam.BOARDS);
        given()
                .contentType(ContentType.JSON)
                .body(signup)
                .when()
                .post("/api/v1/auth/signup");

        AuthResponse auth = given()
                .contentType(ContentType.JSON)
                .body(new SignupRequest("sanitizer_student", "password123", TargetExam.BOARDS))
                .when()
                .post("/api/v1/auth/login")
                .as(AuthResponse.class);
        token = auth.getToken();
    }

    @Test
    public void testJournalXssSanitization() {
        // Inject dirty text containing HTML and active Javascript payload
        String dirtyText = "Today was rough. <script>alert('XSS Attack!')</script> I resolved my maths backlog.";

        JournalRequest request = new JournalRequest(dirtyText);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/v1/journals")
                .then()
                .statusCode(200)
                // Assert the <script> block and script contents have been cleaned out
                .body("entryText", not(containsString("<script>")))
                .body("entryText", not(containsString("alert")));
    }
}
