package provas.temaController;

import dataFactory.TemaDataFactory;
import io.restassured.http.ContentType;
import model.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static dataFactory.TemaDataFactory.temaEscolhido;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static util.TokenUtils.getToken;
import static org.hamcrest.Matchers.equalTo;


public class TemaControllerTest extends TemaDataFactory {
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
    public void testListarTemas() {

        given()
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
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(temaEscolhido())
        .when()
                .post("/tema")
        .then()
                .statusCode(201);
    }

    @Test
    public void testAdicionarTemaJaCadastrado() {

        given()
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
