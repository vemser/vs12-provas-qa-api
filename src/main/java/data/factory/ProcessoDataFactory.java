package data.factory;

import model.Processos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static util.RandomData.FAKER;
import static util.RandomData.RANDOM;

public class ProcessoDataFactory {
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static Processos processoValido() {
        return novoProcesso();
    }
    public static Processos processoInvalido(){return new Processos();}

    private static Processos novoProcesso() {

        int nota = RANDOM.nextInt(1, 10) * 10;
        int numInteiro = RANDOM.nextInt(1, 20);
        boolean questoesPublicas = RANDOM.nextBoolean();

        String[] palavras = {"FACIL", "MEDIO", "DIFICIL"};
        int indice = RANDOM.nextInt(palavras.length);
        String palavraEscolhida = palavras[indice];


        Processos novoProcesso = new Processos();

        novoProcesso.setNome(FAKER.company().name());

        Duration duration = Duration.ofMinutes(300);
        LocalDateTime dataInicio = LocalDateTime.now().plus(duration);
        LocalDateTime dataFim = FAKER.date().future(60, 10, TimeUnit.DAYS).toLocalDateTime();

        novoProcesso.setDataHorarioInicio(dataInicio.format(dtf));
        novoProcesso.setDataHorarioFim(dataFim.format(dtf));

        novoProcesso.setNotaCorte(nota);
        novoProcesso.setDificuldade(palavraEscolhida);
        novoProcesso.setPossuiQuestoesPublicas(questoesPublicas);
        novoProcesso.setQtdFacil(numInteiro);
        novoProcesso.setQtdMedia(numInteiro);
        novoProcesso.setQtdDificil(numInteiro);
        novoProcesso.setIdsTemas(Arrays.asList(1, 2, 3));
        return novoProcesso;
    }
}
