package data.provider;

import data.factory.ProcessoDataFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ProcessoDataProvider {
    public static Stream<Arguments> argumentosInvalidos() {
        return Stream.of(
                Arguments.of(ProcessoDataFactory.gerarInvalidoSemCampoQuestoesPublicas(), "possuiQuestoesPublicas: O processo deve definir se terá ou não questões públicas."),
                Arguments.of(ProcessoDataFactory.gerarInvalidoSemCampoHorarioInicio(), "dataHorarioInicio: A data e horário do início do processo não devem estar em branco."),
                Arguments.of(ProcessoDataFactory.gerarInvalidoSemCampoDificuldade(), "dificuldade: A dificuldade do processo deve não pode estar nulo."),
                Arguments.of(ProcessoDataFactory.gerarInvalidoSemCampoHorarioFim(), "dataHorarioFim: A data e horário do encerramento do processo não podem estar em branco."),
                Arguments.of(ProcessoDataFactory.gerarInvalidoSemCampoNome(), "nome: O nome do processo não pode ficar em branco."),
                Arguments.of(ProcessoDataFactory.gerarInvalidoSemCampoIdTemas(), "idsTemas: O processo seletivo deve conter um tema, não podendo ficar nulo."),
                Arguments.of(ProcessoDataFactory.gerarInvalidoSemCampoNotaDeCorte(), "notaCorte: A nota de corte do processo não pode estar em vazia.")
        );
    }
}
