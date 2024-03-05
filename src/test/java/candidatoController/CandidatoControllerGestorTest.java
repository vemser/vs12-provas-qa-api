package candidatoController;

import client.candidato.CandidatoClient;
import data.factory.CandidatoDataFactory;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.candidato.Candidato;
import model.candidato.CandidatoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static data.factory.CandidatoDataFactory.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.RandomData.FAKER;

@DisplayName("CT-API-02 - Candidato")
@Feature("Candidato - Fluxo Gestor")
public class CandidatoControllerGestorTest {
    private final CandidatoClient client = new CandidatoClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenGestor();
    }

    @Test
    @DisplayName("CT-API-02.2.1 - Adicionar candidato como gestor com sucesso")
    public void testAdicionarCandidatoComSucessoComoGestor(){

        client
                .cadastrar(gerarCandidatoValido(), token)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.2 - Adicionar candidato como gestor utilizando token inválido sem sucesso")
    public void testAdicionarCandidatoComoGestorUtilizandoTokenInvalido(){

        client
                .cadastrar(gerarCandidatoValido(), "TOKEN_INVALIDO")
        .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.3 - Adicionar candidato como gestor com email inválido sem sucesso")
    public void testAdicionarCandidatoComEmailInvalidoComoGestor(){

        client
                .cadastrar(gerarCandidatoComEmailInvalido(), token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.4 - Adicionar candidato como gestor com email já cadastrado com sucesso")
    public void testAdicionarCandidatoComEmailJaCadastradoComoGestor(){

        Response res =
                client
                        .cadastrar(gerarCandidatoValido(), token)
                .then()
                        .extract().response();

        int idCandidado = res.path("idCandidato");
        String emailCandidato = res.path("email");

        Candidato candidatoJahCadastrado = gerarCandidatoValido();
        candidatoJahCadastrado.setEmail(emailCandidato);

        CandidatoResponse candidatoResponse =
                client
                        .cadastrar(candidatoJahCadastrado, token)
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract().as(CandidatoResponse.class)
                ;

        assertAll(
                () -> assertEquals(idCandidado, candidatoResponse.getIdCandidato()),
                () -> assertEquals(emailCandidato, candidatoResponse.getEmail())
        );
    }

    @Test
    @DisplayName("CT-API-02.2.5 - Buscar candidato pelo ID como gestor sem sucesso")
    public void testBuscarCandidatoPeloIdSemSucessoComoGestor(){

        client
                .buscarPorId(FAKER.number().numberBetween(1, 1000), token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.6 - Listar candidatos como gestor acessando a rota de admin sem sucesso")
    public void testListarCandidatosSemSucessoComoGestor(){

        client
                .listar(FAKER.number().numberBetween(0, 10), FAKER.number().numberBetween(1, 15), token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.7 - Listar candidatos como gestor sem sucesso")
    public void testListarCandidatosComoGestorComSucesso(){

        client
                .listarCandidatosComoGestor(FAKER.number().numberBetween(0, 10), FAKER.number().numberBetween(1, 15), token)
                .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.8 - Desativar candidato como gestor sem sucesso")
    public void testDesativarCandidatoSemSucessoComoGestor(){

        client
                .excluir(FAKER.number().numberBetween(1, 100), token)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.9 - Atualizar candidato como gestor com sucesso")
    public void testAtualizarCandidatoComSucessoComoGestor(){

        Response res = client
                .cadastrar(CandidatoDataFactory.gerarCandidatoValido(), token)
                .then()
                .extract().response();

        int idCandidato = res.path("idCandidato");

        Candidato novosDadosCandidato = CandidatoDataFactory.gerarCandidatoValido();

        client
                .atualizar(novosDadosCandidato, idCandidato, token)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("idCandidato", equalTo(idCandidato))
                .body("nome", equalTo(novosDadosCandidato.getNome()))
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.10 - Atualizar candidato com ID inexistente como gestor sem sucesso")
    public void testAtualizarCandidatoComIdInexistenteComoGestorSemSucesso(){

        client
                .atualizar(CandidatoDataFactory.gerarCandidatoValido(), CandidatoDataFactory.gerarIdNaoCadastrado(), token)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DisplayName("CT-API-02.2.11 - Atualizar candidato com formato inválido de ID como gestor sem sucesso")
    public void testAtualizarCandidatoComFormatoInvalidoDeIdComoGestorSemSucesso(){

        client
                .atualizar(CandidatoDataFactory.gerarCandidatoValido(), CandidatoDataFactory.gerarIdInvalido(), token)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

}
