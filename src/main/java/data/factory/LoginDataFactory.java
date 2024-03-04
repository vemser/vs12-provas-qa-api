package data.factory;

import model.login.Login;

import static util.RandomData.FAKER;

public class LoginDataFactory {
    public static Login gerarLoginComEmailMalFormado(){
        Login login = gerarLoginDadosAleatorios();
        login.setEmail(FAKER.lorem().word());
        return login;
    }

    public static Login gerarLoginComEmailNulo(){
        Login login = gerarLoginDadosAleatorios();
        login.setEmail(null);
        return login;
    }

    public static Login gerarLoginComSenhaNula(){
        Login login = gerarLoginDadosAleatorios();
        login.setSenha(null);
        return login;
    }

    public static Login gerarLoginComSenhaMenorQueOPermitido(){
        Login login = gerarLoginDadosAleatorios();
        login.setSenha(FAKER.lorem().characters(1, 4));
        return login;
    }

    public static Login gerarLoginComSenhaMaiorQueOPermitido(){
        Login login = gerarLoginDadosAleatorios();
        login.setSenha(FAKER.lorem().characters(31, 50));
        return login;
    }

    public static Login gerarLoginComDadosValidos(){
        String email = System.getenv("EMAIL_ADM");
        String senha = System.getenv("SENHA_ADM");

        return new Login(email, senha);
    }

    public static Login gerarLoginComDadosNaoCadastrados(){
        return gerarLoginDadosAleatorios();
    }

    private static Login gerarLoginDadosAleatorios(){
        return new Login(
            FAKER.internet().emailAddress(),
            FAKER.internet().password()
        );
    }
}
