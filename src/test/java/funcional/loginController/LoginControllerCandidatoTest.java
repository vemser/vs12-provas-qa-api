package funcional.loginController;

import client.login.LoginClient;
import data.factory.LoginDataFactory;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;

@DisplayName("CT-API-10 - Login")
@Feature("Login - Fluxo Candidato")
public class LoginControllerCandidatoTest {

    private final LoginClient client = new LoginClient();

    @Test
    @DisplayName("CT-API-10.3.1 - Logar usuário com credenciais corretas de candidato com sucesso.")
    public void logarUsuarioComCredenciaisCorretasComSucesso(){
        client
                .logar(LoginDataFactory.gerarLoginCandidatoComDadosValidos())
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body(containsString("Bearer"));
    }
}
