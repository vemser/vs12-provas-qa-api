package dataFactory;

import net.datafaker.Faker;

import model.Processos;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ProcessoDataFactory {

    private static Faker faker = new Faker(new Locale("pt-BR"));
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static Processos processoValido() {
        return novoProcesso();
    }

    private static Processos novoProcesso() {
        Random random = new Random();
        int nota = random.nextInt(1, 10) * 10;
        int numInteiro = random.nextInt(1, 20);
        boolean questoesPublicas = random.nextBoolean();

        String[] palavras = {"FACIL", "MEDIO", "DIFICIL"};
        int indice = random.nextInt(palavras.length);
        String palavraEscolhida = palavras[indice];

        Processos novoProcesso = new Processos();
        novoProcesso.setNome(faker.company().name());
        Date dataInicio = faker.date().future(2, TimeUnit.DAYS);
        Date dataFim = faker.date().future(29, TimeUnit.DAYS);
        novoProcesso.setDataHorarioInicio(sdf.format(dataInicio));
        novoProcesso.setDataHorarioFim(sdf.format(dataFim));
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
