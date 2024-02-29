package dataFactory;

import model.Alternativa;
import model.Questao;

import static util.RandomData.FAKER;

public class QuestaoDataFactory {

    private static final String[] NIVEL_DIFICULDADE = {"FACIL", "MEDIO", "DIFICIL"};
    public static Questao gerarQuestaoValida(){
        return gerarQuestaoAleatoria();
    }

    public static Questao gerarQuestaoInvalidaSemEnunciadoEDificuldade(){
        Questao questao = gerarQuestaoAleatoria();
        questao.setEnunciado("");
        questao.setDificuldade("");

        return questao;
    }

    private static Questao gerarQuestaoAleatoria(){

        int quantidadeDeAlternativas = FAKER.number().numberBetween(2, 5);
        Alternativa[] listaAlternativas = new Alternativa[quantidadeDeAlternativas];

        boolean existeAlternativaCorreta = false;
        for(int i = 0; i < quantidadeDeAlternativas; i++ ){
            Alternativa alternativa = new Alternativa();
            alternativa.setAlternativa(FAKER.lorem().sentence());
            if(!existeAlternativaCorreta){
                boolean isRespostaCorreta = FAKER.random().nextBoolean();
                alternativa.setCorreta(isRespostaCorreta);
                existeAlternativaCorreta = isRespostaCorreta;
            } else {
                alternativa.setCorreta(false);
            }

            listaAlternativas[i] = alternativa;
        }

        if(!existeAlternativaCorreta){
            listaAlternativas[0].setCorreta(true);
        }

        Questao questao = new Questao();
        questao.setTitulo(FAKER.lorem().word());
        questao.setEnunciado(FAKER.lorem().sentence());
        questao.setDificuldade(NIVEL_DIFICULDADE[FAKER.number().numberBetween(0, NIVEL_DIFICULDADE.length)]);
        questao.setIdTemas(new int[]{FAKER.number().numberBetween(1, 5)});
        questao.setAlternativas(listaAlternativas);
        questao.setIdEmpresa(0);

        return questao;
    }
}
