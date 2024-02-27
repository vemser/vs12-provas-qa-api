package util;

import io.restassured.http.ContentType;
import model.Login;
import specs.InitialSpecs;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class AuthUtils {

    private static final String LOGIN_URL = "/auth/login";

    private static String getToken(Login loginData) throws IOException {

        String res = given()
                .spec(InitialSpecs.setup())
                .contentType(ContentType.JSON)
                .body(loginData)
                .when()
                .post(LOGIN_URL)
                .then()
                .extract().body().asString();

        return res.contains("Bearer")
                ? res
                : null;
    }

    public static String getTokenAdmin() {
        String email = System.getenv("EMAIL_ADM");
        String senha = System.getenv("SENHA_ADM");

        try{
            return getToken(new Login(email, senha));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
