package data.provider;

import data.factory.TemaDataFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TemaDataProvider {
    public static Stream<Arguments> argumentosInvalidos() {
        return Stream.of(
                Arguments.of(TemaDataFactory.gerarTemaComNomeNulo(), "nome: must not be null"),
                Arguments.of(TemaDataFactory.gerarTemaComNomeVazio(), "nome: size must be between 3 and 30"),
                Arguments.of(TemaDataFactory.gerarTemaComNomeMenorQueOPermitido(), "nome: size must be between 3 and 30")
        );
    }
}