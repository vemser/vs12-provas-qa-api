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
    public ProcessoClient() {
        super(new ProcessoSpecs());
    }
}

