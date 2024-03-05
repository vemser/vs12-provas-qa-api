package empresaController;

import client.empresa.EmpresaClient;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.empresa.Empresa;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;


import static data.factory.EmpresaDataFactory.*;
import static data.factory.FuncionarioDataFactory.gerarFuncionarioComoCandidato;
import static data.factory.FuncionarioDataFactory.novoFuncionarioNaEmpresa;

@DisplayName("CT-API-01 - Empresa")
@Feature("Empresa - Fluxo Admin")
public class EmpresaControllerTest {
    private static final EmpresaClient client = new EmpresaClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-01.1 - Listar empresas com sucesso")
    public void testListarEmpresas() {

        client.listar(2, 5, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-01.2 - Listar empresas com token inválido sem sucesso")
    public void testListarEmpresasComTokenInvalido() {

        client.listar(2, 5, "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-01.3 - Buscar empresa por ID com sucesso")
    public void testBuscarEmpresaPorIdComSucesso() {

        Response response =
                client.cadastrar(gerarEmpresaValida(), token)
                        .then()
                        .extract().response();

        int idEmpresa = response.path("idEmpresa");

        client.buscarPorId(idEmpresa,token)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("CT-API-01.4 - Buscar empresa por ID utilizando token inválido sem sucesso")
    public void testBuscarEmpresaPorIdUtilizandoTokenInvalido() {

        int idEmpresa = 0;

        client.buscarPorId(idEmpresa, "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("CT-API-01.5 - Buscar empresa por ID inválido sem sucesso")
    public void testBuscarEmpresaPorIdInvalido() {

        int idEmpresa = -1;

        client.buscarPorId(idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        ;
    }


    @Test
    @DisplayName("CT-API-01.6 - Buscar empresa pelo CNPJ com sucesso")
    public void testBuscarEmpresaPeloCnpjComSucesso() {

        Response response =
                client.cadastrar(gerarEmpresaValida(), token)
                        .then()
                        .extract().response();

        String cnpj = response.path("cnpj");

        client.buscarPorCNPJ(cnpj, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-01.7 - Buscar empresa pelo CNPJ inexistente sem sucesso")
    public void testBuscarEmpresaPeloCnpjInexistente() {

        String cnpj = "0";

        client.buscarPorCNPJ(cnpj, token)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DisplayName("CT-API-01.8 - Buscar empresa pelo CNPJ com token inválido sem sucesso")
    public void testBuscarEmpresaPeloCnpjComTokenInvalido() {

        String cnpj = "0";

        client.buscarPorCNPJ(cnpj, "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-01.9 - Adicionar empresa com sucesso")
    public void testAdicionarEmpresaComSucesso() {

        client.cadastrar(gerarEmpresaValida(), token)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("CT-API-01.10 - Adicionar empresa com token inválido sem sucesso")
    public void testAdicionarEmpresaComTokenInvalido() {

        client.cadastrar(gerarEmpresaValida(), "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-01.11 - Adicionar empresa sem CNPJ sem sucesso")
    public void testAdicionarEmpresaSemCnpj() {

        client.cadastrar(gerarEmpresaSemCNPJ(), token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-01.12 - Adicionar empresa sem nome sem sucesso")
    public void testAdicionarEmpresaSemNome() {

        client.cadastrar(gerarEmpresaSemNome(), token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-01.13 - Adicionar empresa sem nome de funcionário sem sucesso")
    public void testAdicionarEmpresaSemNomeDeFuncionario() {

        client.cadastrar(gerarEmpresaSemNomeDeFuncionario(), token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-01.14 - Adicionar funcionário na empresa com sucesso")
    public void testAdicionarFuncionarioNaEmpresa() {

        client.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), 1, token)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("CT-API-01.15 - Adicionar funcionário na empresa com token inválido sem sucesso")
    public void testAdicionarFuncionarioNaEmpresaComTokenInvalido() {

        client.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), 1, "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-01.16 - Adicionar funcionário como candidato sem sucesso")
    public void testAdicionarFuncionarioComoCandidato() {

        client.cadastrarFuncionarioNaEmpresa(gerarFuncionarioComoCandidato(), 1, token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-01.17 - Desativar empresa por ID com sucesso")
    public void testDesativarEmpresaPorID() {

        Response response =
                client.cadastrar(gerarEmpresaValida(), token)
                .then()
                .extract().response();
        ;

        int idEmpresa = response.path("idEmpresa");

        client.excluir(idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("CT-API-01.18 - Desativar empresa por ID inexistente sem sucesso")
    public void testDesativarEmpresaPorIDInexistente() {

        int idEmpresa = 999999999;

        client.excluir(idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("CT-API-01.19 - Atualizar empresa com sucesso")
    public void testAtualizarEmpresaComSucesso() {

        Response response = client.cadastrar(gerarEmpresaValida(), token)
                .then()
                .extract().response();
        ;

        int idEmpresa = response.path("idEmpresa");

        client.atualizar(gerarEmpresaValida(), idEmpresa, token)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("CT-API-01.20 - Atualizar empresa com token inválido sem sucesso")
    public void testAtualizarEmpresaComTokenInvalido() {

        int idEmpresa = 0;

        client.atualizar(gerarEmpresaValida(), idEmpresa, "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}


