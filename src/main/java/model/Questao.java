package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Questao {
    @JsonIgnore
    private String idQuestao;
    private String titulo;
    private String enunciado;
    private String dificuldade;
    private int[] idTemas;
    private Alternativa[] alternativas;
    private int idEmpresa;
}
