package client.candidato;

import client.BaseClient;
import io.restassured.response.Response;
import model.candidato.Candidato;
import specs.candidato.CandidatoSpecs;

import static io.restassured.RestAssured.given;

public class CandidatoClient extends BaseClient<Candidato> {

    private final String COMPLEMENTO_GETOR_PATH = "/gestor/";

    public CandidatoClient() {
        super(new CandidatoSpecs());
    }

    public Response listarCandidatosComoGestor(Integer pagina, Integer quantidadeRegistro, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec())
                        .header("Authorization", authToken)
                        .param("pagina", pagina)
                        .param("quantidadeRegistros", quantidadeRegistro)
                .when()
                        .get(COMPLEMENTO_GETOR_PATH)
                ;
    }

}

