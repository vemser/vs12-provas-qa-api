package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processos {

    String nome;
    String dataHorarioInicio;
    String dataHorarioFim;
    Integer notaCorte;
    String dificuldade;
    Boolean possuiQuestoesPublicas;
    Integer qtdFacil;
    Integer qtdMedia;
    Integer qtdDificil;
    List<Integer> temas;

}
