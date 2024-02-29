package questaoController;

import client.questao.QuestaoClient;
import dataFactory.QuestaoDataFactory;
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
import static util.RandomData.FAKER;

public class QuestaoControllerTest {
    private String token;
    private final QuestaoClient client = new QuestaoClient();

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("Buscar questão por ID")
    public void buscarQuestaoPorId(){

        client
            .buscarPorId(1, token)
        .then()
            .statusCode(200)
        ;
    }
    @Test
    @DisplayName("Buscar questão por ID inexistente")
    public void buscarQuestaoPorIdInexistente(){

        client
                .buscarPorId(FAKER.internet().uuid(), token)
        .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Cadastrar questão com sucesso")
    public void cadastrarQuestaoComSucesso() {
        Questao questao = QuestaoDataFactory.gerarQuestaoValida();

        client
                .cadastrar(questao, token)
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