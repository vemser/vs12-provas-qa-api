package client.processo;

import client.BaseClient;
import io.restassured.response.Response;
import model.Candidato;
import model.Processos;
import specs.ISpecs;
import specs.candidato.CandidatoSpecs;
import specs.processo.ProcessoSpecs;

import static io.restassured.RestAssured.given;

public class ProcessoClient extends BaseClient<Processos> {
    private static final String EMPRESA_ID_PATH = "empresa/{_id}";
    public ProcessoClient() {
        super(new ProcessoSpecs());
    }

    public Response cadastrarEmpresa(Processos processo, Integer idEmpresa, String authToken) {
        return
                given()
                        .spec(SPECS.requestSpec(processo))
                        .header("Authorization", authToken)
                        .pathParam("_id", idEmpresa)
                        .body(processo)
                        .when()
                        .post(EMPRESA_ID_PATH);
    }
}

