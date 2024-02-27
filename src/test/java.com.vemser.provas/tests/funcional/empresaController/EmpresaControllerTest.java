package tests.funcional.empresaController;

import io.restassured.http.ContentType;
import model.EmpresaValida;
import model.Funcionario;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import specs.InitialSpecs;
import util.AuthUtils;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EmpresaControllerTest extends EmpresaValida {
    private static Faker faker = new Faker(new Locale("PT-BR"));
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    public void testListarEmpresas() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .param("pagina", "2")
                .param("quantidadeRegistros", "5")
        .when()
                .get("/empresa")
        .then()
                .statusCode(200)
        ;
    }

    @Test
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
    public void testAdicionarEmpresaComSucesso() {

        EmpresaValida empresa = new EmpresaValida();
        empresa.setNome(faker.company().name());
        empresa.setCnpj(cnpj());
        empresa.setEmail(faker.internet().emailAddress());
        empresa.setNomeFuncionario(faker.name().firstName());

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(empresa)
        .when()
                .post("/empresa")
        .then()
                .statusCode(201)
        ;
    }

    @Test
    public void testAdicionarEmpresaSemCnpj() {

        EmpresaValida empresa = new EmpresaValida();
        empresa.setNome(faker.company().name());
        empresa.setEmail(faker.internet().emailAddress());
        empresa.setNomeFuncionario(faker.name().firstName());

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(empresa)
            .when()
                .post("/empresa")
            .then()
                .statusCode(400)
        ;
    }

    @Test
    public void testAdicionarEmpresaSemNome() {

        EmpresaValida empresa = new EmpresaValida();
        empresa.setCnpj(cnpj());
        empresa.setEmail(faker.internet().emailAddress());
        empresa.setNomeFuncionario(faker.name().firstName());

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(empresa)
            .when()
                .post("/empresa")
            .then()
                .statusCode(400)
        ;
    }

    @Test
    public void testAdicionarEmpresaSemNomeDeFuncionario() {

        EmpresaValida empresa = new EmpresaValida();
        empresa.setNome(faker.company().name());
        empresa.setCnpj(cnpj());
        empresa.setEmail(faker.internet().emailAddress());

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(empresa)
            .when()
                .post("/empresa")
            .then()
                .statusCode(400)
        ;
    }

    @Test
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


