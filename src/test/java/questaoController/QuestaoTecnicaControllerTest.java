package questaoController;

import client.questao.QuestaoTecnicaClient;
import data.factory.QuestaoDataFactory;
import io.qameta.allure.Feature;
import model.questao.tecnica.QuestaoTecnica;
import model.questao.tecnica.QuestaoTecnicaResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import util.AuthUtils;

import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;
import static util.RandomData.FAKER;

@DisplayName("CT-API-04 - Questão Técnica")
@Feature("Questão Técnica - Fluxo Admin")
public class QuestaoTecnicaControllerTest {
    private String token;
    private final QuestaoTecnicaClient client = new QuestaoTecnicaClient();

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-04.2.1 - Cadastrar questão técnica com sucesso")
    public void cadastrarQuestaoTecnicaComSucesso() {

        QuestaoTecnica questaoAhSerCadastrada = QuestaoDataFactory.gerarQuestaoTecnicaValida();

        QuestaoTecnicaResponse questaoCadastrada =
                client
                        .cadastrar(questaoAhSerCadastrada, token)
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract().as(QuestaoTecnicaResponse.class)
                ;

        assertAll(
                () -> assertNotNull(questaoCadastrada.getIdQuestao()),
                () -> assertEquals(questaoAhSerCadastrada.getEnunciado(), questaoCadastrada.getEnunciado()),
                () -> assertEquals(questaoAhSerCadastrada.getCasoTestes().size(), questaoCadastrada.getCasoTestes().size()),
                () -> assertEquals(questaoAhSerCadastrada.getTemplates().size(), questaoCadastrada.getTemplates().size())
        );
    }

    @Test
    @DisplayName("CT-API-04.2.2 - Cadastrar questão técnica com token inválido sem sucesso")
    public void cadastrarQuestaoTecnicaComTokenInvalidoSemSucesso() {

            client
                    .cadastrar(QuestaoDataFactory.gerarQuestaoTecnicaValida(), "TOKEN_INVALIDO")
            .then()
                    .statusCode(HttpStatus.SC_UNAUTHORIZED)
            ;
    }

    @Test
    @DisplayName("CT-API-04.2.4 - Atualizar questão técnica com sucesso")
    public void atualizarQuestaoTecnicaComSucesso() {

        QuestaoTecnicaResponse questaoAhSerAtualizada =
            client
                    .cadastrar(QuestaoDataFactory.gerarQuestaoTecnicaValida(), token)
            .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract().as(QuestaoTecnicaResponse.class)
            ;

        QuestaoTecnicaResponse questaoAtualizada =
            client
                    .atualizar(QuestaoDataFactory.gerarQuestaoTecnicaValida(), questaoAhSerAtualizada.getIdQuestao(), token)
            .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(QuestaoTecnicaResponse.class)
            ;

        assertAll(
                () -> assertEquals(questaoAhSerAtualizada.getIdQuestao(), questaoAtualizada.getIdQuestao()),
                () -> assertNotEquals(questaoAhSerAtualizada.getEnunciado(), questaoAtualizada.getEnunciado()),
                () -> assertNotEquals(questaoAhSerAtualizada.getTitulo(), questaoAtualizada.getTitulo())
        );
    }

    @Test
    @DisplayName("CT-API-04.2.5 - Atualizar questão técnica informando token inválido sem sucesso")
    public void atualizarQuestaoTecnicaComTokenInvalidoSemSucesso() {

        QuestaoTecnica questao = QuestaoDataFactory.gerarQuestaoTecnicaValida();

        client
                .atualizar(questao, FAKER.number().numberBetween(1, 100), "TOKEN_INVALIDO")
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @ParameterizedTest
    @MethodSource("data.provider.QuestaoDataProvider#argumentosInvalidosQuestaoTecnica")
    @DisplayName("CT-API-04.2.3 - Cadastrar questão técnica sem informar campos obrigatórios sem sucesso")
    public void cadastrarQuestaoTecnicaSemInformarCamposObrigatorios(QuestaoTecnica questao, String mensagem) {

        client
                .cadastrar(questao, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", contains(mensagem))
        ;

    }
}
