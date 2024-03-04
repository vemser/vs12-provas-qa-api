package model.questao.tecnica;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.tema.TemaResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestaoTecnicaResponse {
    private Integer idQuestao;
    private boolean ativo;
    private String titulo;
    private String enunciado;
    private String dificuldade;
    private String tipoQuestao;
    private List<TemaResponse> temas;
    private List<TemplateQuestaoTecnica> templates;
    private List<CasoTesteQuestaoProvaTecnica> casoTestes;
    private Integer idEmpresa;
}
