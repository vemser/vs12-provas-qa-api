package client.questao;

import client.BaseClient;
import model.Questao;
import specs.questao.QuestaoSpecs;

public class QuestaoClient extends BaseClient<Questao> {
    public QuestaoClient() {
        super(new QuestaoSpecs());
    }
}
