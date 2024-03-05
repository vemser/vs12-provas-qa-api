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

        int qtdQuestoes = FAKER.number().numberBetween(2, 30);
        int qtdObjetivas = FAKER.number().numberBetween(0, qtdQuestoes);
        int qtdTecnicas = qtdQuestoes - qtdObjetivas;

        int qtdFacilObjetiva = FAKER.number().numberBetween(0, qtdObjetivas);
        int qtdMediaObjetiva = FAKER.number().numberBetween(0, qtdObjetivas - qtdFacilObjetiva);
        int qtdDificilObjetiva = qtdObjetivas - qtdFacilObjetiva - qtdMediaObjetiva;

        int qtdFacilTecnica = FAKER.number().numberBetween(0, qtdTecnicas);
        int qtdMediaTecnica = FAKER.number().numberBetween(0, qtdTecnicas - qtdFacilTecnica);
        int qtdDificilTecnica = qtdTecnicas - qtdFacilTecnica - qtdMediaTecnica;

        novoProcesso.setNotaCorte(RANDOM.nextInt(1, 10) * 10);
        novoProcesso.setDificuldade(dificuldade);
        novoProcesso.setPossuiQuestoesPublicas(RANDOM.nextBoolean());
        novoProcesso.setQtdDificilObjetiva(qtdDificilObjetiva);
        novoProcesso.setQtdMedioObjetiva(qtdMediaObjetiva);
        novoProcesso.setQtdFacilObjetiva(qtdFacilObjetiva);
        novoProcesso.setQtdDificilTecnica(qtdDificilTecnica);
        novoProcesso.setQtdMedioTecnica(qtdMediaTecnica);
        novoProcesso.setQtdFacilTecnica(qtdFacilTecnica);
        novoProcesso.setIdsTemas(Arrays.asList(
                FAKER.number().numberBetween(1, 3),
                FAKER.number().numberBetween(3, 6)
                ));
        novoProcesso.setIdEmpresa(FAKER.number().numberBetween(0, 100));

        return novoProcesso;
    }
}
