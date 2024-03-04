package model.questao.objetiva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativaQuestaoObjetiva {
    private String alternativa;
    private boolean correta = false;
}
