package temaController;

import dataFactory.TemaDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import specs.InitialSpecs;
import util.AuthUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class TemaControllerTest extends TemaDataFactory {
    private String token;

    @BeforeEach
    public void setup() {
       this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    public void testListarTemas() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param("pagina", "0")
                .param("quantidadeRegistros", "10")
        .when()
                .get("/tema")
        .then()
                .statusCode(200)
        ;
    }
    @Test
    public void testAdicionarTemaComSucesso() {


        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(TemaDataFactory.gerarTemaValido())
        .when()
                .post("/tema")
        .then()
                .statusCode(201);
    }

    @Test
    public void testAdicionarTemaJaCadastrado() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body("{\"nome\": \"" + "Luna" + "\"}")
        .when()
                .post("/tema")
        .then()
                .statusCode(400)
                .body("message", equalTo("Tema já cadastrado."));
    }
}
