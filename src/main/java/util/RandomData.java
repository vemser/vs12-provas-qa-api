package util;

import net.datafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class RandomData {
    public static final Faker FAKER = new Faker(Locale.forLanguageTag("PT-BR"));
    public static final Random RANDOM = new Random();
}
