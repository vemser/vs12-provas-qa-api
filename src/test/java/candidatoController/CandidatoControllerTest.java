package candidatoController;

import client.candidato.CandidatoClient;
import dataFactory.CandidatoDataFactory;
import model.Candidato;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static org.hamcrest.Matchers.equalTo;

public class CandidatoControllerTest extends Candidato {
    private final CandidatoClient candidatoClient = new CandidatoClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenAdmin();
    }

    @Test
    public void testAdicionarCandidatoComSucessoComoAdmin(){

        candidatoClient
                .cadastrar(CandidatoDataFactory.gerarCandidatoValido(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    public void testAdicionarCandidatoComEmailInvalidoComoAdmin(){

        candidatoClient
                .cadastrar(CandidatoDataFactory.gerarCandidatoComEmailInvalido(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", Matchers.contains("email: must be a well-formed email address"))
        ;
    }

    @Test
    public void testAdicionarCandidatoComEmailJaCadastradoComoAdmin(){

        candidatoClient
                .cadastrar(CandidatoDataFactory.gerarCandidatoComEmailJahCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testBuscarCandidatoPeloIdComSucessoComoAdmin(){

        candidatoClient
                .buscarPorId(2, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("idCandidato", equalTo(2))
        ;
    }

    @Test
    public void testBuscarCandidatoComIdInvalidoSemSucessoComoAdmin(){

        candidatoClient
                .buscarPorId(CandidatoDataFactory.gerarIdInvalido(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    public void testBuscarCandidatoComIdNaoCadastradoSemSucessoComoAdmin(){

        candidatoClient
                .buscarPorId(CandidatoDataFactory.gerarIdNaoCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Candidato não encontrado."))
        ;
    }

    @Test
    public void testListarCandidatosComSucessoComoAdmin(){

        candidatoClient
                .listar(0, 10, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
        ;
    }
    @Test
    public void testListarCandidatosSemSucessoInformandoPaginaInvalidaComoAdmin(){
        candidatoClient
                .listar(-1, 10, token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
        ;
    }

    @Test
    public void testDesativarCandidatoComSucessoComoAdmin(){

        candidatoClient
                .excluir(2, token)
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    public void testDesativarCandidatoComIdNaoCadastradoSemSucessoComoAdmin(){

        candidatoClient
                .excluir(CandidatoDataFactory.gerarIdNaoCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message",equalTo("Candidado não existe."))
        ;
    }
}
