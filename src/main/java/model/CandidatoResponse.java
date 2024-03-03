package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoResponse {
    private String email;
    private String nome;
    private Integer idCandidato;
    private Integer idEmpresa;
    private String senha;
}
