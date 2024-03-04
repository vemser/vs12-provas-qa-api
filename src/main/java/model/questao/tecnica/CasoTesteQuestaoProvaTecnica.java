package model.questao.tecnica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CasoTesteQuestaoProvaTecnica {
    private String entrada;
    private String saida;
    private Integer publico;
}
