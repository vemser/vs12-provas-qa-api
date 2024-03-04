package data.factory;

import model.tema.Tema;


import static util.RandomData.FAKER;

public class TemaDataFactory {

    public static Tema gerarTemaValido(){
        return new Tema(FAKER.lorem().characters(3,10));
    }

    public static Tema gerarTemaComNomeNulo(){
        return new Tema();
    }

    public static Tema gerarTemaComNomeVazio(){
        return new Tema("");
    }
    public static Tema gerarTemaComNomeMenorQueOPermitido(){
        return new Tema(FAKER.lorem().characters(1, 2));
    }

}

