package candidatoController;

import client.candidato.CandidatoClient;
import data.factory.CandidatoDataFactory;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.Candidato;
import model.CandidatoResponse;
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


@Feature("Candidato - Fluxo Admin")
public class CandidatoControllerAdminTest extends Candidato {
    private final CandidatoClient client = new CandidatoClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("CT-API-02.1.1 - Adicionar candidato como administrador com sucesso")
    public void testAdicionarCandidatoComSucessoComoAdmin(){

        client
                .cadastrar(gerarCandidatoValido(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }
    @Test
    @DisplayName("CT-API-02.1.2 - Adicionar candidato como administrador com email já cadastrado com sucesso")
    public void testAdicionarCandidatoComEmailJaCadastradoComoAdministrador(){

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
    @DisplayName("CT-API-02.1.3 - Adicionar candidato como administrador utilizando token inválido sem sucesso")
    public void testAdicionarCandidatoComoAdminUtilizandoTokenInvalido(){

        client
                .cadastrar(gerarCandidatoValido(), "TOKEN_INVALIDO")
        .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.4 - Adicionar candidato com email inválido como administrador sem sucesso")
    public void testAdicionarCandidatoComEmailInvalidoComoAdmin(){

        client
                .cadastrar(gerarCandidatoComEmailInvalido(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.5 - Buscar candidato pelo ID com sucesso como administrador")
    public void testBuscarCandidatoPeloIdComSucessoComoAdmin(){

        this.token = AuthUtils.getTokenGestor();
        Response res = client
                .cadastrar(CandidatoDataFactory.gerarCandidatoValido(), token)
                .then()
                .extract().response();

        int idCandidato = res.path("idCandidato");
        String emailCandido = res.path("email");

        this.token = AuthUtils.getTokenAdmin();
        client
                .buscarPorId(idCandidato, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("idCandidato", equalTo(idCandidato))
                .body("email", equalTo(emailCandido))
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.6 - Buscar candidato pelo ID utilizando token inválido")
    public void testBuscarCandidatoPeloIdUtilizandoTokenInvalido(){

        client
                .buscarPorId(FAKER.number().positive(), "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.7 - Buscar candidato com ID inválido sem sucesso como administrador")
    public void testBuscarCandidatoComIdInvalidoSemSucessoComoAdmin(){

        client
                .buscarPorId(CandidatoDataFactory.gerarIdInvalido(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.8 - Buscar candidato pelo ID não cadastrado sem sucesso como administrador")
    public void testBuscarCandidatoComIdNaoCadastradoSemSucessoComoAdmin(){

        client
                .buscarPorId(gerarIdNaoCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.9 - Listar candidatos com sucesso como administrador")
    public void testListarCandidatosComSucessoComoAdmin(){

        client
                .listar(0, 10, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.10 - Listar candidatos utilizando token inválido")
    public void testListarCandidatosUtilizandoTokenInvalido(){

        client
                .listar(0, 10, "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
        ;
    }
    @Test
    @DisplayName("CT-API-02.1.11 - Listar candidatos sem sucesso informando página inválida como administrador")
    public void testListarCandidatosSemSucessoInformandoPaginaInvalidaComoAdmin(){
        client
                .listar(-1, 10, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.12 - Desativar candidato com sucesso como administrador")
    public void testDesativarCandidatoComSucessoComoAdmin(){

        this.token = AuthUtils.getTokenGestor();

        Response response =
                client.cadastrar(gerarCandidatoValido(), token)
                        .then().extract().response();

        int idCandidato = response.jsonPath().getInt("idCandidato");


        this.token = AuthUtils.getTokenAdmin();

        client
                .excluir(idCandidato, token)
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.13 - Desativar candidato com ID não cadastrado sem sucesso como administrador")
    public void testDesativarCandidatoComIdNaoCadastradoSemSucessoComoAdmin() {

        client
                .excluir(gerarIdNaoCadastrado(), token)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.14 - Desativar candidato com token inválido")
    public void testDesativarCandidatoComTokenInvalido(){

        client
                .excluir(2, "TOKEN_INVALIDO")
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
        ;
    }

    @Test
    @DisplayName("CT-API-02.1.15 - Atualizar candidato como administrador com sucesso")
    public void testAtualizarCandidatoComoAdminComSucesso(){

        this.token = AuthUtils.getTokenGestor();
        Response res = client
                .cadastrar(CandidatoDataFactory.gerarCandidatoValido(), token)
        .then()
                .extract().response();

        int idCandidato = res.path("idCandidato");

        this.token = AuthUtils.getTokenAdmin();

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
    @DisplayName("CT-API-02.1.16 - Atualizar candidato com ID inexistente como administrador sem sucesso")
    public void testAtualizarCandidatoComIdInexistenteComoAdminSemSucesso(){

        client
                .atualizar(CandidatoDataFactory.gerarCandidatoValido(), CandidatoDataFactory.gerarIdNaoCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }
    @Test
    @DisplayName("CT-API-02.1.17 - Atualizar candidato com formato inválido de ID como administrador sem sucesso")
    public void testAtualizarCandidatoComFormatoInvalidoDeIdComoAdminSemSucesso(){

        client
                .atualizar(CandidatoDataFactory.gerarCandidatoValido(), CandidatoDataFactory.gerarIdInvalido(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }
}
