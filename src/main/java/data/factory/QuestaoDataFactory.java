package data.factory;

import model.Alternativa;
import model.questao.QuestaoObjetiva;

import static util.RandomData.FAKER;

public class QuestaoDataFactory {

    private static final String[] NIVEL_DIFICULDADE = {"FACIL", "MEDIO", "DIFICIL"};
    public static QuestaoObjetiva gerarQuestaoObjetivaValida(){
        return gerarQuestaoObjetivaAleatoria();
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemEnunciado(){
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setEnunciado("");

        return questaoObjetiva;
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemDificuldade(){
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setDificuldade(null);

        return questaoObjetiva;
    }
    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemTipo(){
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setTipoQuestao(null);

        return questaoObjetiva;
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemNenhumTema(){
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setIdTemas(new int[0]);

        return questaoObjetiva;
    }
    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaSemAlternativas(){
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        questaoObjetiva.setAlternativas(new Alternativa[0]);

        return questaoObjetiva;
    }

    public static QuestaoObjetiva gerarQuestaoObjetivaInvalidaComMaisDeUmaOpcaoCorreta(){
        QuestaoObjetiva questaoObjetiva = gerarQuestaoObjetivaAleatoria();
        Alternativa[] alternativas = questaoObjetiva.getAlternativas();
        alternativas[0].setCorreta(true);
        alternativas[1].setCorreta(true);

        questaoObjetiva.setAlternativas(alternativas);
        return questaoObjetiva;
    }

    private static QuestaoObjetiva gerarQuestaoObjetivaAleatoria(){

        int quantidadeDeAlternativas = FAKER.number().numberBetween(2, 5);
        Alternativa[] listaAlternativas = new Alternativa[quantidadeDeAlternativas];

        for(int i = 0; i < quantidadeDeAlternativas; i++ ){
            Alternativa alternativa = new Alternativa();
            alternativa.setAlternativa(FAKER.lorem().sentence());
            alternativa.setCorreta(false);

            listaAlternativas[i] = alternativa;
        }

        listaAlternativas[FAKER.number().numberBetween(0, listaAlternativas.length-1)].setCorreta(true);

        QuestaoObjetiva questaoObjetiva = new QuestaoObjetiva();
        questaoObjetiva.setTitulo(FAKER.lorem().word());
        questaoObjetiva.setEnunciado(FAKER.lorem().sentence());
        questaoObjetiva.setDificuldade(NIVEL_DIFICULDADE[FAKER.number().numberBetween(0, NIVEL_DIFICULDADE.length)]);
        questaoObjetiva.setIdTemas(new int[]{FAKER.number().numberBetween(1, 5)});
        questaoObjetiva.setAlternativas(listaAlternativas);
        questaoObjetiva.setTipoQuestao("OBJETIVA");
        questaoObjetiva.setIdEmpresa(0);

        return questaoObjetiva;
    }
}
