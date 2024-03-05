package util;

import io.restassured.http.ContentType;
import model.login.Login;
import specs.BaseSpecs;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class AuthUtils {

    private static final String LOGIN_URL = "/auth/login";

    private static String getToken(Login loginData) throws IOException {

        String res = given()
                .spec(BaseSpecs.setup())
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
    public static String getTokenGestor() {
        String email = System.getenv("EMAIL_GESTOR");
        String senha = System.getenv("SENHA_GESTOR");

        try{
            return getToken(new Login(email, senha));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getTokenCandidato() {
        String email = System.getenv("EMAIL_CANDIDATO");
        String senha = System.getenv("SENHA_CANDIDATO");

        try{
            return getToken(new Login(email, senha));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
