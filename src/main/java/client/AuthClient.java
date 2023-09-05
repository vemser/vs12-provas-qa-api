package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Login;
import static io.restassured.RestAssured.given;

public class AuthClient extends BaseClient {

    private static final String AUTH = "/login";

    public AuthClient(){
        BaseClient.initConfig();
    }

    public Response logar (Login login){
        return
                given()
                        .contentType(ContentType.JSON)
                        .body(login)
                .when()
                        .post(AUTH)
                ;
    }

}
