package model.empresa;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    private String nome;
    private String cnpj;
    private String email;
    private String cargo;
    private String nomeFuncionario;
}
