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
import static util.RandomData.FAKER;

@DisplayName("CT-API-01 - Empresa")
@Feature("Empresa - Fluxo Candidato")
public class EmpresaControllerCandidatoTest {
    private static final EmpresaClient client = new EmpresaClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenCandidato();
    }

    @Test
    @DisplayName("CT-API-01.3.1 - Listar empresas como candidato sem sucesso")
    public void testListarEmpresasComoCandidatoSemSucesso() {

        client.listar(2, 5, token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-01.3.2 - Buscar empresa pelo CNPJ como candidato sem sucesso")
    public void testBuscarEmpresaPeloCnpjComoCandidatoInexistente() {

        String cnpj = "0";

        client.buscarPorCNPJ(cnpj, token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-01.3.3 - Adicionar empresa como candidato sem sucesso")
    public void testAdicionarEmpresaComoCandidatoSemSucesso() {

        client.cadastrar(gerarEmpresaValida(), token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-01.3.4 - Adicionar funcionário na empresa como candidato sem sucesso")
    public void testAdicionarFuncionarioNaEmpresaComoCandidato() {

        client.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), 1, token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-01.3.5 - Adicionar funcionário como candidato com login de gestor sem sucesso")
    public void testAdicionarFuncionarioComoCandidatoComLoginDeCandidato() {

        client.cadastrarFuncionarioNaEmpresa(gerarFuncionarioComoCandidato(), 1, token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-01.3.6 - Desativar empresa por ID como candidato sem sucesso")
    public void testDesativarEmpresaPorIDInexistenteComoCandidato() {

        client.excluir(FAKER.number().positive(), token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}


