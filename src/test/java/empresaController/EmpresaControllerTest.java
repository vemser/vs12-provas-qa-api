package empresaController;

import client.empresa.EmpresaClient;
import io.restassured.http.ContentType;
import model.Empresa;
import model.Funcionario;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.InitialSpecs;
import util.AuthUtils;

import java.util.Locale;

import static dataFactory.EmpresaDataFactory.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EmpresaControllerTest extends Empresa {
    private static Faker faker = new Faker(new Locale("PT-BR"));
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
        Integer idEmpresa = 5;

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
            .when()
                .get("/empresa/" + idEmpresa )
            .then()
                .log().all()
                .statusCode(200)
                .body("idEmpresa", equalTo(5))
                .body("nome", equalTo("menor preco 02"))
                .body("cnpj", equalTo("12352479000104"))
        ;
    }

    @Test
    @DisplayName("Buscar empresa por ID inexistente")
    public void testBuscarEmpresaPorIdInexistente() {
        Integer idEmpresa = 9612;

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
            .when()
                .get("/empresa/" + idEmpresa)
            .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Empresa não encontrada."))
        ;
    }

    @Test
    @DisplayName("Buscar empresa pelo CNPJ com sucesso")
    public void testBuscarEmpresaPeloCnpjComSucesso() {
        String cnpjEmpresa = "90123456789012";

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
        .when()
                .get("/empresa/cnpj/" + cnpjEmpresa)
        .then()
                .statusCode(200)
                .body("idEmpresa", equalTo(3))
                .body("nome", equalTo("Empresa 3"))
                .body("cnpj", equalTo("90123456789012"))
        ;
    }

    @Test
    @DisplayName("Buscar empresa pelo CNPJ inexistente")
    public void testBuscarEmpresaPeloCnpjInexistente() {
        String cnpjEmpresa = "90123456794372";

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
       .when()
                .get("/empresa/cnpj/" + cnpjEmpresa)
       .then()
                .statusCode(404)
                .body("message", equalTo("Empresa não encontrada."))
        ;
    }

    @Test
    @DisplayName("Adicionar empresa com sucesso")
    public void testAdicionarEmpresaComSucesso() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(gerarEmpresaValida())
        .when()
                .post("/empresa")
        .then()
                .statusCode(201)
        ;
    }

    @Test
    @DisplayName("Adicionar empresa sem CNPJ")
    public void testAdicionarEmpresaSemCnpj() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(gerarEmpresaSemCNPJ())
            .when()
                .post("/empresa")
            .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Adicionar empresa sem nome")
    public void testAdicionarEmpresaSemNome() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(gerarEmpresaSemNome())
            .when()
                .post("/empresa")
            .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Adicionar empresa sem nome de funcionário")
    public void testAdicionarEmpresaSemNomeDeFuncionario() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(gerarEmpresaSemNomeDeFuncionario())
            .when()
                .post("/empresa")
            .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Adicionar funcionário na empresa")
    public void testAdicionarFuncionarioNaEmpresa() {
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail(faker.internet().emailAddress());
        funcionario.setCargo("ROLE_MODERADOR");
        funcionario.setNome(faker.name().firstName());

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(funcionario)
           .when()
                .post("/empresa/1/funcionario")
                .then()
                .statusCode(201)
                .body("email", equalTo(funcionario.getEmail()))
                .body("cargo", equalTo("ROLE_MODERADOR"))
                .body("nome", equalTo(funcionario.getNome()))
        ;
    }
    @Test
    @DisplayName("Adicionar funcionário como candidato")
    public void testAdicionarFuncionarioComoCandidato() {
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail(faker.internet().emailAddress());
        funcionario.setCargo("ROLE_CANDIDATO");
        funcionario.setNome(faker.name().firstName());

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(funcionario)
            .when()
                .post("/empresa/1/funcionario")
           .then()
                .statusCode(400)
                .body("message", equalTo("O funcionário deve ser gestor ou moderador!"))
        ;
    }
}


