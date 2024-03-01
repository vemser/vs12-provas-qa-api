package temaController;

import client.tema.TemaClient;
import data.factory.TemaDataFactory;
import model.Tema;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import util.AuthUtils;

import static org.hamcrest.Matchers.*;

public class TemaControllerTest extends TemaDataFactory {

    private final TemaClient client = new TemaClient();
    private String token;

    @BeforeEach
    public void setup() {
       this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("Listar temas com sucesso")
    public void testListarTemas() {

        client.listar(0,10,token)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Listar temas com token inválido sem sucesso")
    public void testListarTemasComTokenInvalido() {

        client.listar(0,10, "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }
    @Test
    @DisplayName("Adicionar tema com sucesso sem sucesso")
    public void testAdicionarTemaComSucesso() {

        client.cadastrar(TemaDataFactory.gerarTemaValido(), token)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Adicionar tema com token inválido")
    public void testAdicionarTemaComTokenInvalido() {

        client.cadastrar(TemaDataFactory.gerarTemaValido(), "TOKEN_INVALIDO")
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Adicionar tema já cadastrado sem sucesso")
    public void testAdicionarTemaJaCadastrado() {

        client.cadastrar(new Tema("JAVA"), token)
                .then()
                .statusCode(400);
    }

    @ParameterizedTest
    @MethodSource("data.provider.TemaDataProvider#argumentosInvalidos")
    @DisplayName("CT-API-03.4 - Adicionar tema com dados invalidos")
    public void testAdicionarTemaComDadosInvalidosSemSucesso(Tema tema, String mensagem) {

        client
                .cadastrar(tema, token)
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", contains(mensagem));
    }
}
