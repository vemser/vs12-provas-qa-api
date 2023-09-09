package util;

import io.restassured.http.ContentType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TokenUtils {

    public static String getToken(String filePath, String uri, String endpoint) throws IOException {

        filePath = filePath.replaceAll("%20", " ");

        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(filePath);
        properties.load(fileInputStream);

        String email = properties.getProperty("email");
        String senha = properties.getProperty("senha");

        String payload = "{\"email\": \"" + email + "\", \"senha\": \"" + senha + "\"}";

        return given()
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .post(uri + endpoint)
        .then()
                .log().all()
                .extract().asString();

    }
}
