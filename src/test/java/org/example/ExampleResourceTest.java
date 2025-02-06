package org.example;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@ConnectWireMock
class ExampleResourceTest {

    WireMock wiremock;

    @BeforeEach
    void stubResponse() {
        wiremock.register(
                get("/endpoint").willReturn(aResponse().withBody("hey"))
        );
    }


    @Test
    void testHelloEndpoint() {
        for (var a = 0; a < 500; a++) {
            given()
                    .when().get("/hello")
                    .then()
                    .statusCode(200)
                    .body(is("hey"));
        }
        Awaitility.await().atMost(Duration.ofSeconds(2)).untilAsserted(() -> {
            wiremock.verifyThat(500, getRequestedFor(urlPathMatching("/endpoint")));
        });
    }

}