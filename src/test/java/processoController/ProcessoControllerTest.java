package processoController;

import client.processo.ProcessoClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static dataFactory.ProcessoDataFactory.processoInvalido;
import static dataFactory.ProcessoDataFactory.processoValido;

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
    @DisplayName("Buscar processo por ID com sucesso")
    public void testBuscarProcessoPorIdComSucesso() {

        int idEmpresa = 1;

        Response response =
                client.cadastrarEmpresa(processoValido(), idEmpresa, token)
                .then()
                .extract().response();

        int idProcesso = response.jsonPath().getInt("idProcesso");

        client.buscarPorId(idProcesso, token)
                .then()
                .statusCode(200);
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
                client.cadastrarEmpresa(processoValido(), idEmpresa, token)
                .then()
                .extract().response();

        int idProcesso = response.jsonPath().getInt("idProcesso");

        client.excluir(idProcesso, token)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Adicionar processo com sucesso")
    public void testAdicionarProcessoComSucesso() {

        int idEmpresa = 1;

        client.cadastrarEmpresa(processoValido(), idEmpresa, token)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Adicionar processo vazio")
    public void testAdicionarProcessoVazio() {

        int idEmpresa = 1;

        client.cadastrarEmpresa(processoInvalido(), idEmpresa, token)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Atualizar processo com sucesso")
    public void testAtualizarProcessoComSucesso() {

        int idEmpresa = 1;

        Response response =
                client.cadastrarEmpresa(processoValido(), idEmpresa, token)
                .then()
                .extract().response();

        int idProcesso = response.jsonPath().getInt("idProcesso");

        client.atualizar(processoValido(), idProcesso, token)
                .then()
                .statusCode(200);
    }
}
