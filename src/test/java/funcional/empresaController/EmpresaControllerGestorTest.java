package funcional.empresaController;

import client.empresa.EmpresaClient;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static data.factory.EmpresaDataFactory.gerarEmpresaValida;
import static data.factory.FuncionarioDataFactory.gerarFuncionarioComoCandidato;
import static data.factory.FuncionarioDataFactory.novoFuncionarioNaEmpresa;

@DisplayName("CT-API-01 - Empresa")
@Feature("Empresa - Fluxo Gestor")
public class EmpresaControllerGestorTest {
    private static final EmpresaClient client = new EmpresaClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenGestor();
    }

    @Test
    @DisplayName("CT-API-01.2.1 - Listar empresas como gestor sem sucesso")
    public void testListarEmpresasComoGestorSemSucesso() {

        client.listar(2, 5, token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }


    @Test
    @DisplayName("CT-API-01.2.2 - Buscar empresa por ID inválido como gestor sem sucesso")
    public void testBuscarEmpresaPorIdInvalidoComoGestor() {

        int idEmpresa = -1;

        client.buscarPorId(idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        ;
    }

    @Test
    @DisplayName("CT-API-01.2.3 - Buscar empresa pelo CNPJ como gestor sem sucesso")
    public void testBuscarEmpresaPeloCnpjComoGestorInexistente() {

        String cnpj = "0";

        client.buscarPorCNPJ(cnpj, token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-01.2.4 - Adicionar empresa como gestor sem sucesso")
    public void testAdicionarEmpresaComoGestorSemSucesso() {

        client.cadastrar(gerarEmpresaValida(), token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-01.2.5 - Adicionar funcionário na empresa como gestor com sucesso")
    public void testAdicionarFuncionarioNaEmpresaComoGestor() {

        client.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), 1, token)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("CT-API-01.2.6 - Adicionar funcionário como candidato com login de gestor sem sucesso")
    public void testAdicionarFuncionarioComoCandidatoComLoginDeGestor() {

        client.cadastrarFuncionarioNaEmpresa(gerarFuncionarioComoCandidato(), 1, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }


    @Test
    @DisplayName("CT-API-01.2.7 - Desativar empresa por ID inexistente como gestor sem sucesso")
    public void testDesativarEmpresaPorIDInexistenteComoGestor() {

        int idEmpresa = 999999999;

        client.excluir(idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}


