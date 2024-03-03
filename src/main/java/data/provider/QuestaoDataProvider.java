package data.provider;

import data.factory.QuestaoDataFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class QuestaoDataProvider {
    public static Stream<Arguments> argumentosInvalidos() {
        return Stream.of(
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemNenhumTema(), "idTemas: Uma questão deve possuir no mínimo um tema."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemDificuldade(), "dificuldade: A dificuldade da questão não pode estar vazio."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemEnunciado(), "enunciado: O corpo textual da questão não pode estar em branco."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemAlternativas(), "alternativas: A questão deve possuir no mínimo duas alternativas."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemTipo(), "tipoQuestao: O tipo de questão não pode estar vazio.")
        );
    }
}
