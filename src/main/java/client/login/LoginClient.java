package client.login;

import client.BaseClient;
import io.restassured.response.Response;
import model.login.Login;
import specs.login.LoginSpecs;

import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient<Login> {
    public LoginClient() {
        super(new LoginSpecs());
    }

    public Response logar(Login loginData) {
        return
                given()
                        .spec(SPECS.requestSpec(loginData))
                .when()
                        .post();
    }
}
