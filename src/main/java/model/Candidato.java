package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidato {
    private String email;
    private String nome;
    @JsonIgnore
    private String idCandidato;
    @JsonIgnore
    private String senha;


}
