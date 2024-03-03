package questaoController;

import client.questao.QuestaoClient;
import data.factory.QuestaoDataFactory;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.questao.QuestaoObjetiva;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import util.AuthUtils;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static util.RandomData.FAKER;

@Feature("Questão Objetiva - Fluxo Admin")
public class QuestaoObjetivaControllerTest {
    private String token;
    private final QuestaoClient client = new QuestaoClient();

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("Buscar questão objetiva por ID com sucesso")
    public void buscarQuestaoPorId(){

        Response res =
                client
                        .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaValida(), token)
                .then()
                        .extract().response();

        int idQuestao = res.path("idQuestao");

        client
                .buscarPorId(idQuestao, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("idQuestao", equalTo(idQuestao))
        ;
    }

    @Test
    @DisplayName("Buscar questão objetiva por ID com token inválido sem sucesso")
    public void buscarQuestaoPorIdComTokenInvalido(){

        client
                .buscarPorId(FAKER.number().numberBetween(1, 100), "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
        ;
    }
    @Test
    @DisplayName("Buscar questão objetiva por ID inexistente sem sucesso")
    public void buscarQuestaoPorIdInexistente(){

        client
                .buscarPorId(FAKER.number().negative(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("Cadastrar questão objetiva com sucesso")
    public void cadastrarQuestaoComSucesso() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaValida(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("Cadastrar questão objetiva com duas opções corretas sem sucesso")
    public void cadastrarQuestaoComDuasOpcoesCorretasComSucesso() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaComMaisDeUmaOpcaoCorreta(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Uma questão só pode ter uma opção correta."))
        ;
    }

    @Test
    @DisplayName("Cadastrar questão objetiva com token inválido")
    public void cadastrarQuestaoComTokenInvalido() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaValida(), "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }

    @Test
    @DisplayName("Deletar questão objetiva com sucesso")
    public void deletarQuestaoComSucesso() {

        Response response = client
                .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaValida(), token)
                .then()
                .extract().response();

        int idQuestao = response.jsonPath().getInt("idQuestao");

        client.excluir(idQuestao, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("Deletar questão objetiva com token inválido")
    public void deletarQuestaoComTokenInvalido() {

        client.excluir(FAKER.number().numberBetween(1, 100), "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
        ;
    }

    @ParameterizedTest
    @MethodSource("data.provider.QuestaoDataProvider#argumentosInvalidos")
    @DisplayName("Cadastrar questão objetiva sem informar campos obrigatórios sem sucesso")
    public void cadastrarQuestaoSemInformarCamposObrigatorios(QuestaoObjetiva questao, String mensagem) {

        client
                .cadastrar(questao, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", contains(mensagem))
        ;

    }
}