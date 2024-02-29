package client.tema;

import client.BaseClient;
import model.Candidato;
import model.Tema;
import specs.candidato.CandidatoSpecs;
import specs.tema.TemaSpecs;

public class TemaClient extends BaseClient<Tema> {
    public TemaClient() {
        super(new TemaSpecs());
    }

}

