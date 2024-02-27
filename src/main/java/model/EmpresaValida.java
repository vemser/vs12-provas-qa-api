package model;

import lombok.*;
import net.datafaker.Faker;

import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaValida {
    String nome;
    String cnpj;
    String email;
    String nomeFuncionario;
}
