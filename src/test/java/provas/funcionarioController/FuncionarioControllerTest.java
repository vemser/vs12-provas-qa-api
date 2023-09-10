package provas.funcionarioController;

import dataFactory.FuncionarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static util.TokenUtils.getToken;

public class FuncionarioControllerTest extends FuncionarioDataFactory {
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
    public void testListarFuncionarios() {

        given()
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
        .when()
                .get("/funcionario/1/funcionario")
        .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void testListarFuncionarioPeloId() {

        given()
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
            .when()
                .get("/funcionario/1/funcionario/8")
            .then()
                .log().all()
                .statusCode(200)
        ;
    }
    @Test
    public void testAdicionarFuncionario() {

        given()
                .log().all()
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(novoFuncionario())
            .when()
                .post("/funcionario")
            .then()
                .log().all()
                .statusCode(201);
        ;
    }
}
