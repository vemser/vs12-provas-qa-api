package data.factory;

import model.Candidato;

import static util.RandomData.FAKER;


public class CandidatoDataFactory {

    public static Candidato gerarCandidatoValido(){
        return gerarCandidatoAleatorio();
    }
    public static Candidato gerarCandidatoComEmailInvalido(){
        Candidato candidato = gerarCandidatoAleatorio();
        candidato.setEmail(FAKER.lorem().word());
        return candidato;
    }

    public static Candidato gerarCandidatoComEmailJahCadastrado(){
        Candidato candidato = gerarCandidatoAleatorio();
        candidato.setEmail("savio@email.com");
        return candidato;
    }
    public static Integer gerarIdInvalido(){
        return -1;
    }

    public static Integer gerarIdNaoCadastrado(){
        return FAKER.number().numberBetween(9999999, 999999999);
    }

    private static Candidato gerarCandidatoAleatorio(){
        return new Candidato(
                FAKER.internet().emailAddress(),
                FAKER.name().firstName()
        );
    }
}
