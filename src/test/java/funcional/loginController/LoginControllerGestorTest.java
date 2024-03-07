package funcional.loginController;

import client.login.LoginClient;
import data.factory.LoginDataFactory;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@DisplayName("CT-API-10 - Login")
@Feature("Login - Fluxo Gestor")
public class LoginControllerGestorTest {

    private final LoginClient client = new LoginClient();

    @Test
    @DisplayName("CT-API-10.2.1 - Logar usuário com credenciais corretas de gestor com sucesso.")
    public void logarUsuarioComCredenciaisCorretasComSucesso(){
        client
                .logar(LoginDataFactory.gerarLoginGestorComDadosValidos())
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body(containsString("Bearer"));
    }
}
