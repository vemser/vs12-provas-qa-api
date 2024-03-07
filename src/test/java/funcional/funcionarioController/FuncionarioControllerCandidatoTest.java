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
@Feature("Funcionário - Fluxo Candidato")
public class FuncionarioControllerCandidatoTest {
    private final FuncionarioClient client = new FuncionarioClient();
    private final EmpresaClient empresaClient = new EmpresaClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenCandidato();
    }

    @Test
    @DisplayName("CT-API-07.3.1 - Listar funcionários como candidato sem sucesso")
    public void testListarFuncionariosComoCandidato() {

        client
                .listar(0, 5, token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }


    @Test
    @DisplayName("CT-API-07.3.2 - Buscar funcionário pelo ID como candidato sem sucesso")
    public void testListarFuncionarioPeloId() {

        client
                .buscarPorId(1, 8, token)
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-07.3.3 - Atualizar funcionário como candidato sem sucesso")
    public void testAtualizarFuncionarioComoCandidato() {

        client.atualizar(
                novoFuncionarioAtualizado(),
                FAKER.number().positive(),
                token
                )
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-07.3.4 - Deletar funcionário por ID como candidato sem sucesso")
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
    @DisplayName("CT-API-07.2.5 - Cadastrar funcionário como candidato com sucesso")
    public void testCadastrarFuncionarioComoCandidato() {
        int idEmpresa = 1;

        empresaClient.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }
}
