package provas.utils;

import io.restassured.http.ContentType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TokenUtils {

    public static String getToken(String filePath, String uri, String endpoint) throws IOException {

        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(filePath);
        properties.load(fileInputStream);

        String email = properties.getProperty("email");
        String password = properties.getProperty("password");

        String payload = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

        return given()
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .post(uri + endpoint)
        .then()
                .extract().path("authorization").toString();

    }
}
