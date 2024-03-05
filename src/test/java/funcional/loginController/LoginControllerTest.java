package funcional.loginController;

import client.login.LoginClient;
import data.factory.LoginDataFactory;
import io.qameta.allure.Feature;
import model.login.Login;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.Matchers.*;

@DisplayName("CT-API-10 - Login")
@Feature("Login de Usuário")
public class LoginControllerTest {

    private final LoginClient client = new LoginClient();

    @ParameterizedTest
    @MethodSource("data.provider.LoginDataProvider#argumentosInvalidos")
    @DisplayName("CT-API-10.1 - Logar usuário com dados inválidos sem sucesso.")
    public void logarUsuarioComDadosInvalidosSemSucesso(Login dadosLogin, String mensagem){
        client
                .logar(dadosLogin)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", contains(mensagem));
    }

    @Test
    @DisplayName("CT-API-10.2 - Logar usuário com credenciais incorretas sem sucesso.")
    public void logarUsuarioComCredenciaisIncorretasSemSucesso(){
        client
                .logar(LoginDataFactory.gerarLoginComDadosNaoCadastrados())
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Usuário ou senha inválidos"));
    }

    @Test
    @DisplayName("CT-API-10.3 - Logar usuário com credenciais corretas com sucesso.")
    public void logarUsuarioComCredenciaisCorretasComSucesso(){
        client
                .logar(LoginDataFactory.gerarLoginComDadosValidos())
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body(containsString("Bearer"));
    }
}
