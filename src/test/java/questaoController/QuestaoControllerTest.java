package questaoController;

import client.questao.QuestaoClient;
import dataFactory.QuestaoDataFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

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

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoValida(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
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
}