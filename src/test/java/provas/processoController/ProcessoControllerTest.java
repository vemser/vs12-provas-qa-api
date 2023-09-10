package provas.processoController;

import dataFactory.ProcessoDataFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.EmpresaValida;
import model.Processos;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static util.TokenUtils.getToken;

public class ProcessoControllerTest extends ProcessoDataFactory {
    private static Faker faker = new Faker(new Locale("pt-BR"));
    private String token;

    @BeforeEach
    public void setup() {
        baseURI = "http://vemser-hml.dbccompany.com.br:39000/vemser/vs12-provas-back";

        try {
            String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("config.properties")).getPath();
            this.token = getToken(filePath, baseURI, "/auth/login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListarProcessos() {

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param("pagina", "1")
                .param("quantidadeRegistros", "5")
            .when()
                .get("/processo")
            .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void testBuscarProcessoPorIdComSucesso() {
        Integer idProcesso = 6;

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
            .when()
                .get("/processo/" + idProcesso)
            .then()
                .log().all()
                .statusCode(200)
                .body("idProcesso", equalTo(6))
                .body("nome", equalTo("Vem ser DBC"))
                .body("ativo", equalTo(false))
                .body("idEmpresa", equalTo(6))
        ;
    }

    @Test
    public void testBuscarProcessoInexistente() {
        Integer idProcesso = 99;

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .when()
                .get("/processo/" + idProcesso)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Processo não encontrado."))
        ;
    }

    @Test
    public void testDeleteProcessoPorId() {
        String idEmpresa = (faker.number().digit());

        Response response = given()
                .log().all()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(processoValido())
            .when()
                .post("/processo/empresa/" + idEmpresa)
            .then()
                .log().all()
                .statusCode(201)
                .extract().response();


        String idProcesso = response.jsonPath().getString("idProcesso");

        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param(idProcesso)
            .when()
                .delete("/processo/" + idProcesso)
            .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void testAdicionarProcessoComSucesso() {
        String idEmpresa = (faker.number().digit());
        given()
                .log().all()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(processoValido())
            .when()
                .post("/processo/empresa/" + idEmpresa)
            .then()
                .log().all()
                .statusCode(201)
        ;
    }

    @Test
    public void testAtualizarProcessoComSucesso() {
        String idEmpresa = "1";

        Response response = given()
                .log().all()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(processoValido())
           .when()
                .post("/processo/empresa/" + idEmpresa)
           .then()
                .log().all()
                .statusCode(201)
                .extract().response();


        String idProcesso = response.jsonPath().getString("idProcesso");


        given()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(processoValido())
            .when()
                .put("/processo/" + idProcesso)
            .then()
                .log().all()
                .statusCode(200);
    }
}
