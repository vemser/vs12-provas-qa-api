package model.questao.objetiva;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoObjetiva {
    @JsonIgnore
    private String idQuestao;
    private String titulo;
    private String enunciado;
    private String dificuldade;
    private String tipoQuestao;
    private int[] idTemas;
    private AlternativaQuestaoObjetiva[] alternativas;
    private int idEmpresa;
}
