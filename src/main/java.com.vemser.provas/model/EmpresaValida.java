package model;

import lombok.*;

import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaValida {
    String nome;
    String cnpj;
    String email;
    String nomeFuncionario;

    public static String cnpj() {
        Random random = new Random();
        StringBuilder cnpj = new StringBuilder();

        for (int i = 0; i < 14; i++) {
            cnpj.append(random.nextInt(10));
        }
        return cnpj.toString();
    }
}
