package data.provider;

import data.factory.QuestaoDataFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class QuestaoDataProvider {
    public static Stream<Arguments> argumentosInvalidosQuestaoObjetiva() {
        return Stream.of(
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemNenhumTema(), "errors", "idTemas: Uma questão deve possuir no mínimo um tema."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemDificuldade(), "errors", "dificuldade: A dificuldade da questão não pode estar vazio."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemEnunciado(), "errors", "enunciado: O corpo textual da questão não pode estar em branco."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemAlternativas(), "message", "Uma questão deve ter no mínimo duas alternativas."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoObjetivaInvalidaSemTipo(), "errors", "tipoQuestao: O tipo de questão não pode estar vazio.")
        );
    }

    public static Stream<Arguments> argumentosInvalidosQuestaoTecnica() {
        return Stream.of(
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemEnunciado(), "errors", "enunciado: O corpo textual da questão não pode estar em branco."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemTitulo(), "errors", "titulo: O título da questão não pode estar em branco."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemDificuldade(), "errors", "dificuldade: A dificuldade da questão não pode estar vazio."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemTipoQuestao(), "errors", "tipoQuestao: O tipo de questão não pode estar vazio."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemNenhumTema(), "errors", "idTemas: Uma questão deve possuir no mínimo um tema."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemNenhumTemplate(), "message", "É necessário no mínimo 1 template para uma questão técnica."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaSemNenhumCasoTestes(), "message", "A questão deve possuir no mínimo dois casos de teste."),
                Arguments.of(QuestaoDataFactory.gerarQuestaoTecnicaInvalidaComTituloMaiorQueOLimite(), "errors", "titulo: O tamanho máximo para o título são de 60 caracteres")
        );
    }
}

