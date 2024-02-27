package tests.funcional.alternativaController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import specs.InitialSpecs;
import util.AuthUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class AlternativaControllerTest {
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    public void testListarAlternativas() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
        .when()
                .get("/alternativa")
        .then()
                .statusCode(200)
        ;
    }
    @Test
    public void testListarAlternativaPeloId() {

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
        .when()
                .get("/alternativa/1")
        .then()
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
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .param("pagina", "0")
                .param("quantidadeRegistros", "5")
            .when()
                .get("/alternativa/231")
            .then()
                .statusCode(404)
                .body("message", equalTo("Alternativa não encontrada com o ID fornecido: 231") )
        ;
    }
}
