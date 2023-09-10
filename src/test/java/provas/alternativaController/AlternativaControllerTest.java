package provas.alternativaController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static util.TokenUtils.getToken;
import static org.hamcrest.Matchers.equalTo;


public class AlternativaControllerTest {
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
    public void testListarAlternativas() {

        given()
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
        .when()
                .get("/alternativa")
        .then()
                .log().all()
                .statusCode(200)
        ;
    }
    @Test
    public void testListarAlternativaPeloId() {

        given()
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
        .when()
                .get("/alternativa/1")
        .then()
                .log().all()
                .statusCode(200)
                .body("alternativa", equalTo("Alternativa 1") )
                .body("correta", equalTo(true))
                .body("idAlternativa", equalTo(1))
                .body("idQuestao", equalTo(1))
        ;
    }
    @Test
    public void testListarAlternativaPorIdInexistente() {

        given()
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
            .when()
                .get("/alternativa/231")
            .then()
                .log().all()
                .body("status", equalTo(400) )
                .body("message", equalTo("Alternativa não encontrada com o ID fornecido: 231") )
        ;
    }
}
