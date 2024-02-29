package questaoController;

import dataFactory.QuestaoDataFactory;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Alternativa;
import model.Questao;
import specs.InitialSpecs;
import util.AuthUtils;

import static io.restassured.RestAssured.given;

public class QuestaoControllerTest {
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    public void buscarQuestaoPorId(){
        String idQuestao = "19";
        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param(idQuestao)
        .when()
                .get("/questao/20" )
        .then()
                .log().all()
                .statusCode(200)
        ;
    }
    @Test
    public void buscarQuestaoPorIdInexistente(){

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .param("aaaa")
        .when()
                .get("/questao/aaaa" )
        .then()
                .statusCode(400)
        ;
    }

    @Test
    public void cadastrarQuestaoComSucesso() {
        Questao questao = QuestaoDataFactory.gerarQuestaoValida();

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(questao)
                .log().all()
        .when()
                .post("/questao")
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().all()
        ;
    }
    @Test
    public void cadastrarQuestaoSemEnunciadoeDificuldade() {
        Alternativa alternativa1 = new Alternativa();
        alternativa1.setAlternativa("esta é uma alternativa verdadeira");
        alternativa1.setCorreta(true);
        Alternativa alternativa2 = new Alternativa();
        alternativa2.setAlternativa("esta é uma alternativa falsa");

        Questao questao = new Questao();
        questao.setIdTemas(new int[]{1});
        questao.setAlternativas(new Alternativa[]{alternativa1, alternativa2});
        questao.setIdEmpresa(1);

        given()
                .spec(InitialSpecs.setup())
                .header("Authorization", this.token)
                .contentType(ContentType.JSON)
                .body(questao)
        .when()
                .post("/questao")
        .then()
                .statusCode(400)
        ;
    }
}