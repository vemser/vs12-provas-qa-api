package client.questao;

import client.BaseClient;
import model.questao.tecnica.QuestaoTecnica;
import specs.questao.QuestaoTecnicaSpecs;

public class QuestaoTecnicaClient extends BaseClient<QuestaoTecnica> {

    public QuestaoTecnicaClient() {
        super(new QuestaoTecnicaSpecs());
    }

}
