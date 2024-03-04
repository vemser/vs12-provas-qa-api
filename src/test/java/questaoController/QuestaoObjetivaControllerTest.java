package questaoController;

import client.questao.QuestaoObjetivaClient;
import data.factory.QuestaoDataFactory;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.questao.objetiva.QuestaoObjetiva;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import util.AuthUtils;

import static org.hamcrest.Matchers.*;
import static util.RandomData.FAKER;

@Feature("Questão Objetiva - Fluxo Admin")
public class QuestaoObjetivaControllerTest {
    private String token;
    private final QuestaoObjetivaClient client = new QuestaoObjetivaClient();

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-04.1.1 - Listar questões como admin com sucesso")
    public void listarQuestoesComoAdmin(){

        client
                .listar(
                        FAKER.number().numberBetween(0, 20),
                        FAKER.number().numberBetween(1, 30),
                        token
                )
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content", notNullValue())
        ;
    }

    @Test
    @DisplayName("CT-API-04.1.2 - Listar questões utilizando token invalido sem sucesso")
    public void listarQuestoesSemEstarAutenticado(){

        client
                .listar(
                        FAKER.number().numberBetween(0, 20),
                        FAKER.number().numberBetween(1, 30),
                        "TOKEN_INVALIDO"
                )
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-04.1.3 - Buscar questão objetiva por ID com sucesso")
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
    @DisplayName("CT-API-04.1.4 - Buscar questão objetiva por ID com token inválido sem sucesso")
    public void buscarQuestaoPorIdComTokenInvalido(){

        client
                .buscarPorId(FAKER.number().numberBetween(1, 100), "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }
    @Test
    @DisplayName("CT-API-04.1.5 - Buscar questão objetiva por ID inexistente sem sucesso")
    public void buscarQuestaoPorIdInexistente(){

        client
                .buscarPorId(FAKER.number().negative(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-04.1.6 - Cadastrar questão objetiva com sucesso")
    public void cadastrarQuestaoComSucesso() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaValida(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("CT-API-04.1.7 - Cadastrar questão objetiva com duas opções corretas sem sucesso")
    public void cadastrarQuestaoComDuasOpcoesCorretasComSucesso() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaComMaisDeUmaOpcaoCorreta(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Uma questão só pode ter uma opção correta."))
        ;
    }

    @Test
    @DisplayName("CT-API-04.1.8 - Cadastrar questão objetiva com token inválido sem sucesso")
    public void cadastrarQuestaoComTokenInvalido() {

        client
                .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaValida(), "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-04.1.9 - Deletar questão objetiva com sucesso")
    public void deletarQuestaoComSucesso() {

        Response response = client
                .cadastrar(QuestaoDataFactory.gerarQuestaoObjetivaValida(), token)
                .then()
                .extract().response();

        int idQuestao = response.path("idQuestao");

        client.excluir(idQuestao, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-04.1.10 - Deletar questão objetiva com token inválido")
    public void deletarQuestaoComTokenInvalido() {

        client.excluir(FAKER.number().numberBetween(1, 100), "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @ParameterizedTest
    @MethodSource("data.provider.QuestaoDataProvider#argumentosInvalidosQuestaoObjetiva")
    @DisplayName("CT-API-04.1.11 - Cadastrar questão objetiva sem informar campos obrigatórios sem sucesso")
    public void cadastrarQuestaoSemInformarCamposObrigatorios(QuestaoObjetiva questao, String mensagem) {

        client
                .cadastrar(questao, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", contains(mensagem))
        ;

    }
}