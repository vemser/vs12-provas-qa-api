package dataFactory;

import model.Processos;
import net.datafaker.Faker;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProcessoDataFactory extends Processos {

    private static Faker faker = new Faker(new Locale("pt-BR"));

    public static Processos processoValido(){
        return novoProcesso();
    }

    private static  Processos novoProcesso(){
        Random random = new Random();
        int nota = random.nextInt(1, 10)*10;
        int numInteiro = random.nextInt(1, 20);
        boolean questoesPublicas = random.nextBoolean();

        String[] palavras = {"facil", "medio", "dificil"};
        int indice = random.nextInt(palavras.length);
        String palavraEscolhida = palavras[indice];

        Processos novoProcesso = new Processos();
        novoProcesso.setNome(faker.company().name());
        novoProcesso.setDataHorarioInicio(faker.date().future(1, TimeUnit.DAYS).toString());
        novoProcesso.setDataHorarioFim(faker.date().future(2,TimeUnit.DAYS).toString());
        novoProcesso.setNotaCorte(nota);
        novoProcesso.setDificuldade(palavraEscolhida);
        novoProcesso.setPossuiQuestoesPublicas(questoesPublicas);
        novoProcesso.setQtdFacil(numInteiro);
        novoProcesso.setQtdMedia(numInteiro);
        novoProcesso.setQtdDificil(numInteiro);
        novoProcesso.setTemas(Arrays.asList(1, 2, 3));
        return novoProcesso;
    }
}
