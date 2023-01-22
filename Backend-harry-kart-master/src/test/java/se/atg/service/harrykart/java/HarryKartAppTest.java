package se.atg.service.harrykart.java;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("java-test")
public class HarryKartAppTest {

    private final static URI harryKartApp = URI.create("http://localhost:8181/java/api/play");

    @BeforeAll
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }




}
