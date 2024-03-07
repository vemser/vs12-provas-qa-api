package funcional.funcionarioController;

import client.empresa.EmpresaClient;
import client.funcionario.FuncionarioClient;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static data.factory.FuncionarioDataFactory.novoFuncionarioAtualizado;
import static data.factory.FuncionarioDataFactory.novoFuncionarioNaEmpresa;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static util.RandomData.FAKER;

@DisplayName("CT-API-07 - Funcionário")
@Feature("Funcionário - Fluxo Gestor")
public class FuncionarioControllerGestorTest {
    private final FuncionarioClient client = new FuncionarioClient();
    private final EmpresaClient empresaClient = new EmpresaClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenGestor();
    }

    @Test
    @DisplayName("CT-API-07.2.1 - Listar funcionários como gestor sem sucesso")
    public void testListarFuncionariosComoGestor() {

        client
                .listar(0, 5, token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }


    @Test
    @DisplayName("CT-API-07.2.2 - Buscar funcionário pelo ID como gestor sem sucesso")
    public void testListarFuncionarioPeloId() {

        client
                .buscarPorId(1, 8, token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-07.2.3 - Atualizar funcionário como gestor sem sucesso")
    public void testAtualizarFuncionarioComoGestor() {
        int idEmpresa = 1;

        Response response =
                empresaClient.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().response();

        int idNovoFuncionario = response.path("idFuncionario");

        client.atualizar(novoFuncionarioAtualizado(), idNovoFuncionario, token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-07.2.4 - Deletar funcionário por ID como gestor sem sucesso")
    public void testDeletarFuncionarioPorID() {

        client.excluir(
                FAKER.number().numberBetween(1, 100),
                FAKER.number().numberBetween(1, 100),
                token
                )
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-07.2.5 - Cadastrar funcionário como gestor com sucesso")
    public void testCadastrarFuncionarioComoGestor() {
        int idEmpresa = 1;

                empresaClient.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), idEmpresa, token)
                        .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .body("idFuncionario", notNullValue())
                        .body("idEmpresa", equalTo(idEmpresa))
        ;
    }
}
