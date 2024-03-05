package funcional.processoController;

import client.processo.ProcessoClient;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.processo.Processo;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import util.AuthUtils;

import static data.factory.ProcessoDataFactory.gerarProcessoInvalidoTodosOsCamposVazios;
import static data.factory.ProcessoDataFactory.gerarProcessoValido;
import static org.hamcrest.Matchers.*;
import static util.RandomData.FAKER;

@DisplayName("CT-API-06 - Processo")
@Feature("Processo - Fluxo Admin")
public class ProcessoControllerTest {
    private final ProcessoClient client = new ProcessoClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-06.1.1 - Listar processos com sucesso")
    public void testListarProcessos() {

        client
                .listar(FAKER.number().numberBetween(0, 20), FAKER.number().numberBetween(1, 20), token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content", notNullValue())
        ;
    }
    @Test
    @DisplayName("CT-API-06.1.2 - Listar processos informando paginacao invalida sem sucesso")
    public void testListarProcessosComPaginacaoInvalida() {

        client
                .listar(-1, 5, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("findAll.pagina: must be greater than or equal to 0"))
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.3 - Listar processos com token inválido sem sucesso")
    public void testListarProcessosComTokenInvalido() {

        client
                .listar(1, 5, AuthUtils.getTokenInvalidio())
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.4 - Buscar processo por ID com sucesso")
    public void testBuscarProcessoPorIdComSucesso() {


        Processo processo = gerarProcessoValido();
        int idEmpresa = processo.getIdEmpresa();

        Response response =
                client
                        .cadastrarProcesso(processo, idEmpresa, token)
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract().response();

        Integer idProcesso = response.path("idProcesso");

        client
                .buscarPorId(idProcesso, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("idProcesso", equalTo(idProcesso))
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.5 - Buscar processo por ID com token inválido sem sucesso")
    public void testBuscarProcessoPorIdComTokenInvalido() {

        int idProcesso = 0;

        client
                .buscarPorId(idProcesso, AuthUtils.getTokenInvalidio())
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.6 - Buscar processo inexistente sem sucesso")
    public void testBuscarProcessoInexistente() {

        int idProcesso = -1;

        client
                .buscarPorId(idProcesso, token)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.7 - Deletar processo por ID com sucesso")
    public void testDeleteProcessoPorId() {

        Processo processo = gerarProcessoValido();

        Response response =
                client
                        .cadastrarProcesso(processo, processo.getIdEmpresa(), token)
                .then()
                        .extract().response();

        int idProcesso = response.path("idProcesso");

        client
                .excluir(idProcesso, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.8 - Deletar processo por ID com token inválido sem sucesso")
    public void testDeleteProcessoPorIdComTokenInvalido() {

        int idProcesso = 0;

        client
                .excluir(idProcesso, AuthUtils.getTokenInvalidio())
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.9 - Adicionar processo com sucesso")
    public void testAdicionarProcessoComSucesso() {

        Processo processo = gerarProcessoValido();

        client
                .cadastrarProcesso(processo, processo.getIdEmpresa(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("nome", equalTo(processo.getNome()))
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.10 - Adicionar processo com token inválido sem sucesso")
    public void testAdicionarProcessoComSucessoTokenInvalido() {

        int idEmpresa = 1;

        client
                .cadastrarProcesso(gerarProcessoValido(), idEmpresa, AuthUtils.getTokenInvalidio())
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.11 - Adicionar processo vazio sem sucesso")
    public void testAdicionarProcessoVazio() {

        int idEmpresa = 1;

        client
                .cadastrarProcesso(gerarProcessoInvalidoTodosOsCamposVazios(), idEmpresa, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("CT-API-06.1.12 - Atualizar processo com sucesso")
    public void testAtualizarProcessoComSucesso() {

        Processo processo = gerarProcessoValido();

        Response response =
                client
                        .cadastrarProcesso(processo, processo.getIdEmpresa(), token)
                .then()
                        .extract().response();

        int idProcesso = response.path("idProcesso");

        client
                .atualizar(gerarProcessoValido(), idProcesso, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("idProcesso", equalTo(idProcesso))
        ;
    }

    @Test
    @DisplayName("CT-API-06.1.13 - Atualizar processo com token inválido sem sucesso")
    public void testAtualizarProcessoComTokenInvalido() {

        int idProcesso = 0;

        client
                .atualizar(gerarProcessoValido(), idProcesso, AuthUtils.getTokenInvalidio())
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @ParameterizedTest
    @MethodSource("data.provider.ProcessoDataProvider#argumentosInvalidos")
    @DisplayName("CT-API-06.1.14 - Cadastrar processo sem informar campos obrigatórios sem sucesso")
    public void testCadastrarProcessoSemInformarCamposObrigatorios(Processo processo, String mensagem){
        client
                .cadastrarProcesso(processo, processo.getIdEmpresa(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", contains(mensagem))
        ;
    }
}
