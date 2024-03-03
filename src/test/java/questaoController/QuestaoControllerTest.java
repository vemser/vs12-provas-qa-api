package questaoController;

import client.questao.QuestaoClient;
import data.factory.QuestaoDataFactory;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

@Feature("Questão - Fluxo Admin")
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
    @DisplayName("Buscar questão por ID com token inválido")
    public void buscarQuestaoPorIdComTokenInvalido(){

        client
                .buscarPorId(1, "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }
    @Test
    @DisplayName("Buscar questão por ID inexistente")
    public void buscarQuestaoPorIdInexistente(){

        client
                .buscarPorId(-1, token)
        .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Cadastrar questão com sucesso")
    public void cadastrarQuestaoComSucesso() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoValida(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("Cadastrar questão com token inválido")
    public void cadastrarQuestaoComTokenInvalido() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoValida(), "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }
    @Test
    @DisplayName("Cadastrar questão sem enunciando e sem dificuldade sem sucesso")
    public void cadastrarQuestaoSemEnunciadoeDificuldade() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoInvalidaSemEnunciadoEDificuldade(), token)
        .then()
                .statusCode(400)
        ;
    }

    @Test
    @DisplayName("Deletar questão com sucesso")
    public void deletarQuestaoComSucesso() {

        Response response = client
                .cadastrar(QuestaoDataFactory.gerarQuestaoValida(), token)
                .then()
                .extract().response();

        int idQuestao = response.jsonPath().getInt("idQuestao");

        client.excluir(idQuestao, token)
                .then()
                .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Deletar questão com token inválido")
    public void deletarQuestaoComTokenInvalido() {

        int idQuestao = 0;

        client.excluir(idQuestao, "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }
}