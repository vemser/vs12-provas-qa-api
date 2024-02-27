package tests.funcional.processoController;

import dataFactory.ProcessoDataFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Processos;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import specs.InitialSpecs;
import util.AuthUtils;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProcessoControllerTest extends ProcessoDataFactory {
    private static Faker faker = new Faker(new Locale("pt-BR"));
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    public void testListarProcessos() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param("pagina", "1")
                .param("quantidadeRegistros", "5")
            .when()
                .get("/processo")
            .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testBuscarProcessoPorIdComSucesso() {
        Integer idProcesso = 6;

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
            .when()
                .get("/processo/" + idProcesso)
            .then()
                .statusCode(200)
                .body("idProcesso", equalTo(6))
                .body("nome", equalTo("Vem ser DBC"))
                .body("ativo", equalTo(false))
                .body("idEmpresa", equalTo(1))
        ;
    }

    @Test
    public void testBuscarProcessoInexistente() {
        Integer idProcesso = 999999999;

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
           .when()
                .get("/processo/" + idProcesso)
           .then()
                .statusCode(404)
                .body("message", equalTo("Processo não encontrado."))
        ;
    }

    @Test
    public void testDeleteProcessoPorId() {
        String idEmpresa = (faker.number().digit());

        Response response = given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(processoValido())
            .when()
                .post("/processo/empresa/" + idEmpresa)
            .then()
                .statusCode(201)
                .extract().response();


        String idProcesso = response.jsonPath().getString("idProcesso");

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param(idProcesso)
            .when()
                .delete("/processo/" + idProcesso)
            .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testAdicionarProcessoComSucesso() {
        Integer idEmpresa = 0;

      Response response = given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(processoValido())
            .when()
                .post("/processo/empresa/" + idEmpresa)
            .then()
                .statusCode(201)
                .extract().response();

        Integer idProcesso = response.jsonPath().getInt("idProcesso");

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
            .when()
                .get("/processo/" + idProcesso)
            .then()
                .body("idProcesso", equalTo(idProcesso));
    }

    @Test
    public void testAtualizarProcessoComSucesso() {
        String idEmpresa = "1";

        Response response = given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(processoValido())
           .when()
                .post("/processo/empresa/" + idEmpresa)
           .then()
                .extract().response();


        String idProcesso = response.jsonPath().getString("idProcesso");
        Processos processos = processoValido();

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(processos)
            .when()
                .put("/processo/" + idProcesso)
            .then()
                .statusCode(200)
                .body("nome", equalTo(processos.getNome()))
                .body("notaCorte", equalTo(processos.getNotaCorte()))
                .body("dificuldade", equalTo(processos.getDificuldade()));
    }
}
