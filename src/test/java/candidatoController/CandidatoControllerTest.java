package candidatoController;

import client.candidato.CandidatoClient;
import dataFactory.CandidatoDataFactory;
import model.Candidato;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import static org.hamcrest.Matchers.equalTo;

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
                .cadastrar(CandidatoDataFactory.gerarCandidatoValido(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED)
        ;
    }

    @Test
    @DisplayName("Adicionar candidato com email inválido como administrador")
    public void testAdicionarCandidatoComEmailInvalidoComoAdmin(){

        client
                .cadastrar(CandidatoDataFactory.gerarCandidatoComEmailInvalido(), token)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", Matchers.contains("email: must be a well-formed email address"))
        ;
    }

    @Test
    @DisplayName("Adicionar candidato com email já cadastrado como administrador")
    public void testAdicionarCandidatoComEmailJaCadastradoComoAdmin(){

        client
                .cadastrar(CandidatoDataFactory.gerarCandidatoComEmailJahCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @DisplayName("Buscar candidato pelo ID com sucesso como administrador")
    public void testBuscarCandidatoPeloIdComSucessoComoAdmin(){

        client
                .buscarPorId(2, token)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("idCandidato", equalTo(2))
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
                .buscarPorId(CandidatoDataFactory.gerarIdNaoCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Candidato não encontrado."))
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

        client
                .excluir(2, token)
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    @DisplayName("Desativar candidato com ID não cadastrado sem sucesso como administrador")
    public void testDesativarCandidatoComIdNaoCadastradoSemSucessoComoAdmin(){

        client
                .excluir(CandidatoDataFactory.gerarIdNaoCadastrado(), token)
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message",equalTo("Candidado não existe."))
        ;
    }
}
