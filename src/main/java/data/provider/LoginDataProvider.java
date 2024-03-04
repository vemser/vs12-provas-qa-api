package data.provider;

import data.factory.LoginDataFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class LoginDataProvider {
    public static Stream<Arguments> argumentosInvalidos() {
        return Stream.of(
                Arguments.of(LoginDataFactory.gerarLoginComEmailNulo(), "email: must not be null"),
                Arguments.of(LoginDataFactory.gerarLoginComSenhaNula(), "senha: must not be null"),
                Arguments.of(LoginDataFactory.gerarLoginComSenhaMenorQueOPermitido(), "senha: size must be between 6 and 30"),
                Arguments.of(LoginDataFactory.gerarLoginComSenhaMaiorQueOPermitido(), "senha: size must be between 6 and 30"),
                Arguments.of(LoginDataFactory.gerarLoginComEmailMalFormado(), "email: must be a well-formed email address")
        );
    }
}