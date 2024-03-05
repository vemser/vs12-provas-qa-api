package model.candidato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoPutResponse {
        private String email;
        private String nome;
        private Integer idCandidato;
}
