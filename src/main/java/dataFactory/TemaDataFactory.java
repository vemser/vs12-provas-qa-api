package dataFactory;

import model.Tema;
import net.datafaker.Faker;

import java.util.Locale;

public class TemaDataFactory {

    private static Faker faker = new Faker(Locale.forLanguageTag("PT-BR"));

    public static Tema gerarTemaValido(){
        return new Tema(faker.lorem().characters(3,10));
    }

}

