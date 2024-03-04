package data.provider;

import data.factory.QuestaoDataFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class QuestaoDataProvider {
    public static Stream<Arguments> argumentosInvalidosQuestaoObjetiva() {
        return Stream.of(
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemNenhumTema(), "idTemas: Uma questão deve possuir no mínimo um tema."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemDificuldade(), "dificuldade: A dificuldade da questão não pode estar vazio."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemEnunciado(), "enunciado: O corpo textual da questão não pode estar em branco."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemAlternativas(), "alternativas: A questão deve possuir no mínimo duas alternativas."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemTipo(), "tipoQuestao: O tipo de questão não pode estar vazio.")
        );
    }

    public static Stream<Arguments> argumentosInvalidosQuestaoTecnica() {
        return Stream.of(
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemEnunciado(), "enunciado: O corpo textual da questão não pode estar em branco."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemTitulo(),  "titulo: O título da questão não pode estar em branco."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemDificuldade(), "dificuldade: A dificuldade da questão não pode estar vazio."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemTipoQuestao(), "tipoQuestao: O tipo de questão não pode estar vazio."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemNenhumTema(), "idTemas: Uma questão deve possuir no mínimo um tema."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemNenhumTemplate(), "templates: É necessário pelo menos 1 template para uma questão."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemNenhumCasoTestes(), "casoTestes: A questão deve possuir no mínimo dois casos de teste.")
        );
    }
}

