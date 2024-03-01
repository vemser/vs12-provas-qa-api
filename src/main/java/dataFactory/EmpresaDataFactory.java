package dataFactory;

import model.Empresa;
import net.datafaker.Faker;

import java.util.Locale;

public class EmpresaDataFactory {

    private static Faker faker = new Faker(new Locale("PT-BR"));

    public static Empresa gerarEmpresaValida(){
        return empresaValida();
    };

    public static Empresa gerarEmpresaSemCNPJ(){
        return empresaSemCNPJ();
    };

    public static Empresa gerarEmpresaSemNome(){
        return empresaSemNome();
    };

    public static Empresa gerarEmpresaSemNomeDeFuncionario(){
        return empresaSemNomeDeFuncionario();
    };
    private static Empresa empresaValida(){
        return new Empresa(
        faker.company().name(),
        faker.cnpj().valid().replaceAll("[.\\-/]", ""),
        faker.internet().emailAddress(),
        faker.name().firstName());
    }

    private static Empresa empresaSemCNPJ(){
        return new Empresa(
                faker.company().name(),
                null,
                faker.internet().emailAddress(),
                faker.name().firstName());
    }

    private static Empresa empresaSemNome(){
        return new Empresa(
                null,
                faker.cnpj().valid().replaceAll("[.\\-/]", ""),
                faker.internet().emailAddress(),
                faker.name().firstName());
    }

    private static Empresa empresaSemNomeDeFuncionario(){
        return new Empresa(
                faker.company().name(),
                faker.cnpj().valid().replaceAll("[.\\-/]", ""),
                faker.internet().emailAddress(),
                null);
    }

}
