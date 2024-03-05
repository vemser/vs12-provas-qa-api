package data.factory;

import model.empresa.Empresa;

import static util.RandomData.FAKER;

public class EmpresaDataFactory {

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
        FAKER.company().name(),
        FAKER.cnpj().valid().replaceAll("[.\\-/]", ""),
        FAKER.internet().emailAddress(),
        "ROLE_MODERADOR",
        FAKER.name().firstName());
    }

    private static Empresa empresaSemCNPJ(){
        return new Empresa(
                FAKER.company().name(),
                null,
                FAKER.internet().emailAddress(),
                "ROLE_MODERADOR",
                FAKER.name().firstName());
    }

    private static Empresa empresaSemNome(){
        return new Empresa(
               null,
                FAKER.cnpj().valid().replaceAll("[.\\-/]", ""),
                FAKER.internet().emailAddress(),
                "ROLE_MODERADOR",
                FAKER.name().firstName());
    }

    private static Empresa empresaSemNomeDeFuncionario(){
        return new Empresa(
                FAKER.company().name(),
                FAKER.cnpj().valid().replaceAll("[.\\-/]", ""),
                FAKER.internet().emailAddress(),
                "ROLE_MODERADOR",
                null);
    }

}
