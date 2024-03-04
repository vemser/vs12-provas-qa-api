package client.tema;

import client.BaseClient;
import model.tema.Tema;
import specs.tema.TemaSpecs;

public class TemaClient extends BaseClient<Tema> {
    public TemaClient() {
        super(new TemaSpecs());
    }

}

