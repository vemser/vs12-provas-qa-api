package client.candidato;

import client.BaseClient;
import model.Candidato;
import specs.candidato.CandidatoSpecs;

public class CandidatoClient extends BaseClient<Candidato> {
    public CandidatoClient() {
        super(new CandidatoSpecs());
    }

}

