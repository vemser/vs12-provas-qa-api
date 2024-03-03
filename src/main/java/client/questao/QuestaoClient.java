package client.questao;

import client.BaseClient;
import model.questao.QuestaoObjetiva;
import specs.questao.QuestaoSpecs;

public class QuestaoClient extends BaseClient<QuestaoObjetiva> {
    public QuestaoClient() {
        super(new QuestaoSpecs());
    }
}
