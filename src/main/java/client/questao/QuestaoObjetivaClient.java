package client.questao;

import client.BaseClient;
import model.questao.objetiva.QuestaoObjetiva;
import specs.questao.QuestaoObjetivaSpecs;

import static io.restassured.RestAssured.given;

public class QuestaoObjetivaClient extends BaseClient<QuestaoObjetiva> {

    public QuestaoObjetivaClient() {
        super(new QuestaoObjetivaSpecs());
    }

}
