package dataFactory;

import model.Processos;
import net.datafaker.Faker;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProcessoDataFactory {

    private static Faker faker = new Faker(new Locale("pt-BR"));

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
        novoProcesso.setDataHorarioInicio(faker.date().future(1, TimeUnit.DAYS, "yyyy-MM-dd'T'HH:mm:ss'.'sss'Z'"));
        novoProcesso.setDataHorarioFim(faker.date().future(30, TimeUnit.DAYS, "yyyy-MM-dd'T'HH:mm:ss'.'sss'Z'"));
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
