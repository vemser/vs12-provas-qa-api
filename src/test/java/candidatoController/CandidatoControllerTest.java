package candidatoController;

import client.candidato.CandidatoClient;
import data.factory.CandidatoDataFactory;
import io.restassured.response.Response;
import model.Candidato;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static data.factory.CandidatoDataFactory.*;

public class CandidatoControllerTest extends Candidato {
    private final CandidatoClient client = new CandidatoClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    @DisplayName("Adicionar candidato com sucesso como administrador")
    public void testAdicionarCandidatoComSucessoComoAdmin(){

        client
                .cadastrar(gerarCandidatoValido(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("Adicionar candidato utilizando token inválido")
    public void testAdicionarCandidatoUtilizandoTokenInvalido(){

        client
                .cadastrar(gerarCandidatoValido(), "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }

    @Test
    @DisplayName("Adicionar candidato com email inválido como administrador")
    public void testAdicionarCandidatoComEmailInvalidoComoAdmin(){

        client
                .cadastrar(gerarCandidatoComEmailInvalido(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("Adicionar candidato com email já cadastrado como administrador")
    public void testAdicionarCandidatoComEmailJaCadastradoComoAdmin(){

        client
                .cadastrar(gerarCandidatoComEmailJahCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("Buscar candidato pelo ID com sucesso como administrador")
    public void testBuscarCandidatoPeloIdComSucessoComoAdmin(){

        client
                .buscarPorId(58, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("Buscar candidato pelo ID utilizando token inválido")
    public void testBuscarCandidatoPeloIdUtilizandoTokenInvalido(){

        client
                .buscarPorId(2, "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }

    @Test
    @DisplayName("Buscar candidato com ID inválido sem sucesso como administrador")
    public void testBuscarCandidatoComIdInvalidoSemSucessoComoAdmin(){

        client
                .buscarPorId(CandidatoDataFactory.gerarIdInvalido(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("Buscar candidato pelo ID não cadastrado sem sucesso como administrador")
    public void testBuscarCandidatoComIdNaoCadastradoSemSucessoComoAdmin(){

        client
                .buscarPorId(gerarIdNaoCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DisplayName("Listar candidatos com sucesso como administrador")
    public void testListarCandidatosComSucessoComoAdmin(){

        client
                .listar(0, 10, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }

    @Test
    @DisplayName("Listar candidatos utilizando token inválido")
    public void testListarCandidatosUtilizandoTokenInvalido(){

        client
                .listar(0, 10, "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }
    @Test
    @DisplayName("Listar candidatos sem sucesso informando página inválida como administrador")
    public void testListarCandidatosSemSucessoInformandoPaginaInvalidaComoAdmin(){
        client
                .listar(-1, 10, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    @DisplayName("Desativar candidato com sucesso como administrador")
    public void testDesativarCandidatoComSucessoComoAdmin(){

        Response response =
                client.cadastrar(gerarCandidatoValido(), token)
                        .then().extract().response();

        int idCandidato = response.jsonPath().getInt("idCandidato");

        client
                .excluir(idCandidato, token)
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @DisplayName("Desativar candidato com ID não cadastrado sem sucesso como administrador")
    public void testDesativarCandidatoComIdNaoCadastradoSemSucessoComoAdmin() {

        client
                .excluir(gerarIdNaoCadastrado(), token)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DisplayName("Desativar candidato com token inválido")
    public void testDesativarCandidatoComTokenInvalido(){

        client
                .excluir(2, "TOKEN_INVALIDO")
                .then()
                .statusCode(500)
        ;
    }
}
