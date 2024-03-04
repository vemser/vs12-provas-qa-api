package temaController;

import client.tema.TemaClient;
import data.factory.TemaDataFactory;
import io.qameta.allure.Feature;
import model.tema.Tema;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import util.AuthUtils;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("CT-API-03 - Tema")
@Feature("Tema - Fluxo Admin")
public class TemaControllerTest extends TemaDataFactory {

    private final TemaClient client = new TemaClient();
    private String token;

    @BeforeEach
    public void setup() {
       this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-03.1 - Listar temas com sucesso")
    public void testListarTemas() {

        client.listar(0,10,token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-03.2 - Listar temas com token inválido sem sucesso")
    public void testListarTemasComTokenInvalido() {

        client.listar(0,10, "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-03.3 - Adicionar tema com sucesso")
    public void testAdicionarTemaComSucesso() {

        client.cadastrar(TemaDataFactory.gerarTemaValido(), token)
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("CT-API-03.5 - Adicionar tema com token inválido sem sucesso")
    public void testAdicionarTemaComTokenInvalido() {

        client.cadastrar(TemaDataFactory.gerarTemaValido(), "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("CT-API-03.6 - Adicionar tema já cadastrado sem sucesso")
    public void testAdicionarTemaJaCadastrado() {

        client.cadastrar(new Tema("JAVA"), token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("CT-API-03.7 - Listar temas com informando quantidade de registros negativo sem sucesso")
    public void testListarTemasComQuantidadeRegistroNegativa() {

        client.listar(0,-1, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("findAll.quantidadeRegistros: must be greater than 0"))
        ;
    }

    @ParameterizedTest
    @MethodSource("data.provider.TemaDataProvider#argumentosInvalidos")
    @DisplayName("CT-API-03.4 - Adicionar tema com dados invalidos sem sucesso")
    public void testAdicionarTemaComDadosInvalidosSemSucesso(Tema tema, String mensagem) {

        client
                .cadastrar(tema, token)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", contains(mensagem));
    }
}
