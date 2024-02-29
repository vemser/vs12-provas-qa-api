package questaoController;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Buscar questão por ID")
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
    @DisplayName("Buscar questão por ID inexistente")
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
    @DisplayName("Cadastrar questão com sucesso")
    public void cadastrarQuestaoComSucesso() {

        Alternativa alternativa1 = new Alternativa();
        alternativa1.setAlternativa("esta é uma alternativa verdadeira");
        alternativa1.setCorreta(true);
        Alternativa alternativa2 = new Alternativa();
        alternativa2.setAlternativa("esta é uma alternativa falsa");

        Questao questao = new Questao();
        questao.setTitulo("Titulo de questão teste");
        questao.setEnunciado("Enunciado de questão teste");
        questao.setDificuldade("FACIL");
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
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }
    @Test
    @DisplayName("Cadastrar questão sem enunciando e sem dificuldade")
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