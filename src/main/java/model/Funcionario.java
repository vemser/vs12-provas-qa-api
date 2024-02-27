package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    Integer idEmpresa;
    String email;
    String cargo;
    String nome;
    String senha;

}
