package provas.empresaController;

import io.restassured.http.ContentType;
import model.EmpresaValida;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static util.TokenUtils.getToken;
import static org.hamcrest.Matchers.equalTo;

public class EmpresaControllerTest extends EmpresaValida {
    private static Faker faker = new Faker(new Locale("PT-BR"));
    private String token;

    @BeforeEach
    public void setup() {
        baseURI = "http://vemser-hml.dbccompany.com.br:39000/vemser/vs12-provas-back";

        try {
            String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("config.properties")).getPath();
            System.out.println(filePath);
            this.token = getToken(filePath, baseURI, "/auth/login");
            System.out.println(this.token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListarEmpresas() {

        given()
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
                .header("Authorization", this.token)
            .when()
                .get("/empresa/" + idEmpresa )
            .then()
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
                .header("Authorization", this.token)
            .when()
                .get("/empresa/" + idEmpresa)
            .then()
                .statusCode(404)
                .body("message", equalTo("Empresa não encontrada."))
        ;
    }

    @Test
    public void testBuscarEmpresaPeloCnpjComSucesso() {
        String cnpjEmpresa = "90123456789012";

        given()
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
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(empresa)
        .when()
                .post("/empresa")
        .then()
                .log().all()
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
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(empresa)
            .when()
                .post("/empresa")
            .then()
                .log().all()
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
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(empresa)
            .when()
                .post("/empresa")
            .then()
                .log().all()
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
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(empresa)
            .when()
                .post("/empresa")
            .then()
                .log().all()
                .statusCode(400)
        ;
    }
}


