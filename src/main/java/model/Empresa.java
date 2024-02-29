package model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    String nome;
    String cnpj;
    String email;
    String nomeFuncionario;
}
