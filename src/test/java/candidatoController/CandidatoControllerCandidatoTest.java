package candidatoController;

import client.candidato.CandidatoClient;
import data.factory.CandidatoDataFactory;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.candidato.Candidato;
import model.candidato.CandidatoPutResponse;
import model.candidato.CandidatoResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.AuthUtils;

import java.util.Optional;

import static data.factory.CandidatoDataFactory.gerarCandidatoComEmailInvalido;
import static data.factory.CandidatoDataFactory.gerarCandidatoValido;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static util.RandomData.FAKER;

@DisplayName("CT-API-02 - Candidato")
@Feature("Candidato - Fluxo Candidato")
public class CandidatoControllerCandidatoTest {
    private final CandidatoClient client = new CandidatoClient();
    private String token;

    @BeforeEach
    public void setup() {
        this.token = AuthUtils.getTokenCandidato();
    }

    @Test
    @DisplayName("CT-API-02.3.1 - Atualizar candidato como candidato com sucesso")
    public void testAtualizarCandidatoComSucessoComoCandidato() {

        CandidatoPutResponse atualizacaoResponse =
                client
                        .atualizarDadosLogadoComoCandidato(CandidatoDataFactory.gerarDadosAtualizacaoDeCandidato(), token)
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract().as(CandidatoPutResponse.class);

        this.token = AuthUtils.getTokenAdmin();

        Response buscaCandidatoAtualizadoResponse =
                client
                        .buscarPorId(atualizacaoResponse.getIdCandidato(), token)
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract().response();

        int idCandidatoAtualizado = buscaCandidatoAtualizadoResponse.path("idCandidato");
        String nomeCandidatoAtualizado = buscaCandidatoAtualizadoResponse.path("nome");

        assertAll(
                () -> assertEquals(atualizacaoResponse.getIdCandidato(), idCandidatoAtualizado),
                () -> assertEquals(atualizacaoResponse.getNome(), nomeCandidatoAtualizado)
        );
    }

    @Test
    @DisplayName("CT-API-02.3.2 - Atualizar candidato como candidato com token inválido sem sucesso")
    public void testAtualizarCandidatoComTokenInvalidoSemSucessoComoCandidato() {

            client
                    .atualizarDadosLogadoComoCandidato(CandidatoDataFactory.gerarDadosAtualizacaoDeCandidato(), AuthUtils.getTokenInvalidio())
            .then()
                    .statusCode(HttpStatus.SC_UNAUTHORIZED)
            ;
    }


}
