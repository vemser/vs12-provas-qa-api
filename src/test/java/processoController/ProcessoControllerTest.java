package processoController;

import client.processo.ProcessoClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.InitialSpecs;
import util.AuthUtils;

import model.Processos;
import java.util.Locale;

import static dataFactory.ProcessoDataFactory.processoValido;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProcessoControllerTest {
    private final ProcessoClient client = new ProcessoClient();
    private final String PATH_EMPRESA = "empresa";
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
                client.cadastrar(processoValido(), PATH_EMPRESA, idEmpresa, token)
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
                .body("message", equalTo("Processo não encontrado."))
        ;
    }

    @Test
    @DisplayName("Deletar processo por ID")
    public void testDeleteProcessoPorId() {

        int idEmpresa = 1;

        Response response =
                client.cadastrar(processoValido(), PATH_EMPRESA, idEmpresa, token)
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

        client.cadastrar(processoValido(), PATH_EMPRESA, idEmpresa, token)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Atualizar processo com sucesso")
    public void testAtualizarProcessoComSucesso() {

        int idEmpresa = 1;

        Response response =
                client.cadastrar(processoValido(), PATH_EMPRESA, idEmpresa, token)
                .then()
                .extract().response();

        int idProcesso = response.jsonPath().getInt("idProcesso");

        client.atualizar(processoValido(), idProcesso, token)
                .then()
                .statusCode(200);
    }
}
