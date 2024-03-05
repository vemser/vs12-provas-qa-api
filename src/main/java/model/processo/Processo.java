package model.processo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processo {

    String nome;
    String dataHorarioInicio;
    String dataHorarioFim;
    Integer notaCorte;
    String dificuldade;
    Boolean possuiQuestoesPublicas;
    Integer qtdFacilObjetiva;
    Integer qtdMedioObjetiva;
    Integer qtdDificilObjetiva;
    Integer qtdFacilTecnica;
    Integer qtdMedioTecnica;
    Integer qtdDificilTecnica;
    List<Integer> idsTemas;
    Integer idEmpresa;
}