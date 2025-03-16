package ru.sibsutis.petstore.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @LocalServerPort
    private int port;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @DynamicPropertySource
    static void configureProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    @Sql(statements = "INSERT INTO category (id, name) VALUES (1, 'Dogs') ON CONFLICT (id) DO NOTHING")
    void testCreateAndGetPet() {
        String requestBody = """
            {
              "name": "Bobby",
              "category": { "id": 1, "name": "Dogs" },
              "tags": [],
              "status": "available"
            }
        """;

        int petId = RestAssured.given()
                .port(port)
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        RestAssured.given()
                .port(port)
                .when()
                .get("/pet/" + petId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Bobby"))
                .body("status", equalTo("available"));
    }
}
