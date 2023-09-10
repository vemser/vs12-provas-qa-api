package dataFactory;

import model.Tema;

import java.util.Random;

public class TemaDataFactory extends Tema {

    public static String temaEscolhido(){
        return temas();
    }

    private static String temas(){
        String[] temas = {
                "MATEMÁTICA",
                "HISTÓRIA",
                "GEOGRAFIA",
                "CIÊNCIAS",
                "LÍNGUA PORTUGUESA",
                "INGLÊS",
                "ARTES",
                "EDUCAÇÃO FÍSICA",
                "AGILIDADE",
                "JAVA",
                "PYTHON",
                "C++",
                "LOGICA",
        };

        Random random = new Random();
        int indiceAleatorio = random.nextInt(temas.length);
        return "{\"nome\": \"" + temas[indiceAleatorio] + "\"}";

        };

}

