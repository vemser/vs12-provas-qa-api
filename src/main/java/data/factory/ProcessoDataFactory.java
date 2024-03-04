package data.factory;

import model.processo.Processo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static util.RandomData.FAKER;
import static util.RandomData.RANDOM;

public class ProcessoDataFactory {
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static Processo gerarProcessoValido() {
        return novoProcesso();
    }

    public static Processo gerarInvalidoSemCampoQuestoesPublicas() {
        Processo processo = novoProcesso();
        processo.setPossuiQuestoesPublicas(null);
        return processo;
    }

    public static Processo gerarInvalidoSemCampoHorarioInicio() {
        Processo processo = novoProcesso();
        processo.setDataHorarioInicio(null);
        return processo;
    }

    public static Processo gerarInvalidoSemCampoHorarioFim() {
        Processo processo = novoProcesso();
        processo.setDataHorarioFim(null);
        return processo;
    }

    public static Processo gerarInvalidoSemCampoDificuldade() {
        Processo processo = novoProcesso();
        processo.setDificuldade(null);
        return processo;
    }

    public static Processo gerarInvalidoSemCampoNome() {
        Processo processo = novoProcesso();
        processo.setNome(null);
        return processo;
    }

    public static Processo gerarInvalidoSemCampoIdTemas() {
        Processo processo = novoProcesso();
        processo.setIdsTemas(null);
        return processo;
    }

    public static Processo gerarInvalidoSemCampoNotaDeCorte() {
        Processo processo = novoProcesso();
        processo.setNotaCorte(null);
        return processo;
    }

    public static Processo gerarProcessoInvalidoTodosOsCamposVazios() {
        return new Processo();
    }

    private static Processo novoProcesso() {

        String[] niveisDificuldade = {"FACIL", "MEDIO", "DIFICIL"};
        String dificuldade = niveisDificuldade[RANDOM.nextInt(niveisDificuldade.length)];

        Processo novoProcesso = new Processo();

        novoProcesso.setNome(FAKER.company().name());

        Duration duration = Duration.ofMinutes(300);
        LocalDateTime dataInicio = LocalDateTime.now().plus(duration);
        LocalDateTime dataFim = FAKER.date().future(60, 10, TimeUnit.DAYS).toLocalDateTime();

        novoProcesso.setDataHorarioInicio(dataInicio.format(dtf));
        novoProcesso.setDataHorarioFim(dataFim.format(dtf));

        Integer qtdQuestoes = FAKER.number().numberBetween(2, 20);
        Integer qtdObjetivas = FAKER.number().numberBetween(0, qtdQuestoes);
        Integer qtdTecnicas = qtdQuestoes - qtdObjetivas;
        Integer qtdFacil = FAKER.number().numberBetween(0, qtdQuestoes);
        Integer qtdMedia = FAKER.number().numberBetween(0, qtdQuestoes - qtdFacil);
        Integer qtdDificil = qtdQuestoes - qtdFacil - qtdMedia;

        novoProcesso.setNotaCorte(RANDOM.nextInt(1, 10) * 10);
        novoProcesso.setDificuldade(dificuldade);
        novoProcesso.setPossuiQuestoesPublicas(RANDOM.nextBoolean());
        novoProcesso.setQtdObjetivas(qtdObjetivas);
        novoProcesso.setQtdTecnicas(qtdTecnicas);
        novoProcesso.setQtdFacil(qtdFacil);
        novoProcesso.setQtdMedia(qtdMedia);
        novoProcesso.setQtdDificil(qtdDificil);
        novoProcesso.setIdsTemas(Arrays.asList(1, 2, 3));
        novoProcesso.setIdEmpresa(FAKER.number().numberBetween(0, 100));

        return novoProcesso;
    }
}
