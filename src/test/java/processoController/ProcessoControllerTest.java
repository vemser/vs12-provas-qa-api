package processoController;

import client.processo.ProcessoClient;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static data.factory.ProcessoDataFactory.processoInvalido;
import static data.factory.ProcessoDataFactory.processoValido;

@Feature("Processo - Fluxo Admin")
public class ProcessoControllerTest {
    private final ProcessoClient client = new ProcessoClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("Listar processos")
    public void testListarProcessos() {

        client.listar(1, 5, token)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Listar processos com token inválido")
    public void testListarProcessosComTokenInvalido() {

        client.listar(1, 5, "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }

    @Test
    @DisplayName("Buscar processo por ID com sucesso")
    public void testBuscarProcessoPorIdComSucesso() {

        int idEmpresa = 1;

        Response response =
                client.cadastrarProcesso(processoValido(), idEmpresa, token)
                .then()
                .extract().response();

        int idProcesso = response.jsonPath().getInt("idProcesso");

        client.buscarPorId(idProcesso, token)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Buscar processo por ID com token inválido")
    public void testBuscarProcessoPorIdComTokenInvalido() {

        int idProcesso = 0;

        client.buscarPorId(idProcesso, "TOKEN_INVALIDO")
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Buscar processo inexistente")
    public void testBuscarProcessoInexistente() {

        int idProcesso = -1;

        client.buscarPorId(idProcesso, token)
                .then()
                .statusCode(404)
        ;
    }

    @Test
    @DisplayName("Deletar processo por ID")
    public void testDeleteProcessoPorId() {

        int idEmpresa = 1;

        Response response =
                client.cadastrarProcesso(processoValido(), idEmpresa, token)
                .then()
                .extract().response();

        int idProcesso = response.jsonPath().getInt("idProcesso");

        client.excluir(idProcesso, token)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Deletar processo por ID com token inválido")
    public void testDeleteProcessoPorIdComTokenInvalido() {

        int idProcesso = 0;

        client.excluir(idProcesso, "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }

    @Test
    @DisplayName("Adicionar processo com sucesso")
    public void testAdicionarProcessoComSucesso() {

        int idEmpresa = 1;

        client.cadastrarProcesso(processoValido(), idEmpresa, token)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Adicionar processo com token inválido")
    public void testAdicionarProcessoComSucessoTokenInvalido() {

        int idEmpresa = 1;

        client.cadastrarProcesso(processoValido(), idEmpresa, "TOKEN_INVALIDO")
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("Adicionar processo vazio")
    public void testAdicionarProcessoVazio() {

        int idEmpresa = 1;

        client.cadastrarProcesso(processoInvalido(), idEmpresa, token)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Atualizar processo com sucesso")
    public void testAtualizarProcessoComSucesso() {

        int idEmpresa = 1;

        Response response =
                client.cadastrarProcesso(processoValido(), idEmpresa, token)
                .then()
                .extract().response();

        int idProcesso = response.jsonPath().getInt("idProcesso");

        client.atualizar(processoValido(), idProcesso, token)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Atualizar processo com token inválido")
    public void testAtualizarProcessoComTokenInvalido() {

        int idProcesso = 0;

        client.atualizar(processoValido(), idProcesso, "TOKEN_INVALIDO")
                .then()
                .statusCode(500);
    }
}
