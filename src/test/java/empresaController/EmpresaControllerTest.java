package empresaController;

import client.empresa.EmpresaClient;
import io.restassured.response.Response;
import model.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;


import static dataFactory.EmpresaDataFactory.*;
import static dataFactory.FuncionarioDataFactory.gerarFuncionarioComoCandidato;
import static dataFactory.FuncionarioDataFactory.novoFuncionarioNaEmpresa;

public class EmpresaControllerTest extends Empresa {
    private static EmpresaClient client = new EmpresaClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("Listar empresas")
    public void testListarEmpresas() {

        client.listar(2, 5, token)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Buscar empresa por ID com sucesso")
    public void testBuscarEmpresaPorIdComSucesso() {

        Response response =
                client.cadastrar(gerarEmpresaValida(), token)
                        .then()
                        .extract().response();

        int idEmpresa = response.jsonPath().getInt("idEmpresa");

        client.buscarPorId(idEmpresa,token)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Buscar empresa por ID inválido")
    public void testBuscarEmpresaPorIdInvalido() {

        int idEmpresa = -1;

        client.buscarPorId(idEmpresa, token)
                .then()
                .statusCode(400);
        ;
    }

    @Test
    @DisplayName("Buscar empresa pelo CNPJ com sucesso")
    public void testBuscarEmpresaPeloCnpjComSucesso() {

        Response response =
                client.cadastrar(gerarEmpresaValida(), token)
                        .then()
                        .extract().response();

        String cnpj = response.jsonPath().getString("cnpj");

        client.buscarPorCNPJ(cnpj, token)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Buscar empresa pelo CNPJ inexistente")
    public void testBuscarEmpresaPeloCnpjInexistente() {

        String cnpj = "0";

        client.buscarPorCNPJ(cnpj, token)
                .then()
                .statusCode(404)
        ;
    }

    @Test
    @DisplayName("Adicionar empresa com sucesso")
    public void testAdicionarEmpresaComSucesso() {

        client.cadastrar(gerarEmpresaValida(), token)
                .then()
                .statusCode(201)
        ;
    }

    @Test
    @DisplayName("Adicionar empresa sem CNPJ")
    public void testAdicionarEmpresaSemCnpj() {

        client.cadastrar(gerarEmpresaSemCNPJ(), token)
                .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Adicionar empresa sem nome")
    public void testAdicionarEmpresaSemNome() {

        client.cadastrar(gerarEmpresaSemNome(), token)
                .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Adicionar empresa sem nome de funcionário")
    public void testAdicionarEmpresaSemNomeDeFuncionario() {

        client.cadastrar(gerarEmpresaSemNomeDeFuncionario(), token)
                .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Adicionar funcionário na empresa")
    public void testAdicionarFuncionarioNaEmpresa() {

        client.cadastrarFuncionarioNaEmpresa(novoFuncionarioNaEmpresa(), 1, token)
                .then()
                .statusCode(201)
        ;
    }
    @Test
    @DisplayName("Adicionar funcionário como candidato")
    public void testAdicionarFuncionarioComoCandidato() {

        client.cadastrarFuncionarioNaEmpresa(gerarFuncionarioComoCandidato(), 1, token)
                .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Desativar empresa por ID")
    public void testDesativarEmpresaPorID() {

        Response response =
                client.cadastrar(gerarEmpresaValida(), token)
                .then()
                .extract().response();
        ;

        int idEmpresa = response.jsonPath().getInt("idEmpresa");

        client.excluir(idEmpresa, token)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Atualizar empresa com sucesso")
    public void testAtualizarEmpresaComSucesso() {

        Response response = client.cadastrar(gerarEmpresaValida(), token)
                .then()
                .extract().response();
        ;

        int idEmpresa = response.jsonPath().getInt("idEmpresa");

        client.atualizar(gerarEmpresaValida(), idEmpresa, token)
                .then()
                .statusCode(200);
    }
}


