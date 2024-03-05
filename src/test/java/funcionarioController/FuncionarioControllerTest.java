package funcionarioController;

import client.empresa.EmpresaClient;
import client.funcionario.FuncionarioClient;
import static data.factory.FuncionarioDataFactory.*;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

@DisplayName("CT-API-07 - Empresa")
@Feature("Funcionário - Fluxo Admin")
public class FuncionarioControllerTest {
    private final FuncionarioClient client = new FuncionarioClient();
    private final EmpresaClient empresaClient = new EmpresaClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-07.1 - Listar funcionários com sucesso")
    public void testListarFuncionarios() {

        client
                .listar(0, 5, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-07.2 - Listar funcionários com token inválido sem sucesso")
    public void testListarFuncionariosComTokenInvalido() {

        client
                .listar(1, 0, 5, AuthUtils.getTokenInvalidio())
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-07.3 - Listar funcionário pelo ID com sucesso")
    public void testListarFuncionarioPeloId() {

        client
                .buscarPorId(1, 8, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-07.4 - Listar funcionário pelo ID com token inválido sem sucesso")
    public void testListarFuncionarioPeloIdComTokenInvalido() {

        client
                .buscarPorId(1, 8, AuthUtils.getTokenInvalidio())
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-07.5 - Listar funcionário por ID inválido sem sucesso")
    public void testListarFuncionarioPorIDInvalido() {

        client
                .buscarPorId(1, -1, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-07.6 - Atualizar funcionário com sucesso")
    public void testAtualizarFuncionario() {
        int idEmpresa = 1;

        Response response =
                empresaClient.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().response();

        int idNovoFuncionario = response.path("idFuncionario");

        client.atualizar(novoFuncionarioAtualizado(), idNovoFuncionario, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-07.7 - Atualizar funcionário com token inválido sem sucesso")
    public void testAtualizarFuncionarioComTokenInvalido() {

        int idNovoFuncionario = 0;

        client.atualizar(novoFuncionarioAtualizado(), idNovoFuncionario, AuthUtils.getTokenInvalidio())
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-07.8 - Deletar funcionário por ID com sucesso")
    public void testDeletarFuncionarioPorID() {
        int idEmpresa = 1;

        Response response =
                empresaClient.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), idEmpresa, token)
                        .then()
                        .extract().response();

        int idNovoFuncionario = response.path("idFuncionario");

        client.excluir(idEmpresa, idNovoFuncionario, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-07.9 - Deletar funcionário por ID com token inválido sem sucesso")
    public void testDeletarFuncionarioPorIDComTokenInvalido() {

        int idEmpresa = 0;
        int idNovoFuncionario = 0;

        client.excluir(idEmpresa, idNovoFuncionario, AuthUtils.getTokenInvalidio())
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }
}
