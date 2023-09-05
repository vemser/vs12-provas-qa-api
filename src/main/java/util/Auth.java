package util;

import client.AuthClient;
import model.Login;

public class Auth {
    private static final AuthClient authClient = new AuthClient();

    public static String token() {

        Login loginAdm = new Login(
                Manipulation.getProp().getProperty("email"),
                Manipulation.getProp().getProperty("password")
        );

        return authClient.logar(loginAdm)
                .then()
                    .extract().path("authorization")
                ;
    }
}
