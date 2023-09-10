package dataFactory;

import model.Funcionario;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class FuncionarioDataFactory extends Funcionario {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    public static String gerarRole() {
        String[] cargo = {
                "ROLE_MODERADOR",
                "ROLE_DESTOR",
                "ROLE_CANDIDADO",
        };
        Random random = new Random();
        int indice = random.nextInt(cargo.length);
        return cargo[indice];
    }

    public static Funcionario novoFuncionario(){
        Random random = new Random();
        int numInteiro = random.nextInt(1, 20);

        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setIdEmpresa(numInteiro);
        novoFuncionario.setEmail(faker.internet().emailAddress());
        novoFuncionario.setCargo(gerarRole());
        novoFuncionario.setNome(faker.name().firstName());

        return novoFuncionario;
    }
}
