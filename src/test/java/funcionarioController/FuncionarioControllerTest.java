package funcionarioController;

import client.empresa.EmpresaClient;
import client.funcionario.FuncionarioClient;
import dataFactory.FuncionarioDataFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;


public class FuncionarioControllerTest extends FuncionarioDataFactory {
    private static final FuncionarioClient client = new FuncionarioClient();
    private static final EmpresaClient empresaClient = new EmpresaClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("Listar funcionários")
    public void testListarFuncionarios() {

        client
                .listar(1, 0, 5, token)
        .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Listar funcionário pelo ID")
    public void testListarFuncionarioPeloId() {

        client
                .buscarPorId(1, 8, token)
        .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Listar funcionário por ID inválido")
    public void testListarFuncionarioPorIDInvalido() {

        client
                .buscarPorId(1, -1, token)
                .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Atualizar funcionário")
    public void testAtualizarFuncionario() {
        int idEmpresa = 1;

        Response response =
                empresaClient.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), idEmpresa, token)
                .then()
                .statusCode(201)
                .extract().response();

        int idNovoFuncionario = response.jsonPath().getInt("idFuncionario");

        client.atualizar(novoFuncionarioAtualizado(), idNovoFuncionario, token)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Deletar funcionário por ID")
    public void testDeletarFuncionarioPorID() {
        int idEmpresa = 1;

        Response response =
                empresaClient.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), idEmpresa, token)
                        .then()
                        .extract().response();

        int idNovoFuncionario = response.jsonPath().getInt("idFuncionario");

        client.excluir(idEmpresa, idNovoFuncionario, token)
                .then()
                .statusCode(200)
        ;
    }
}
