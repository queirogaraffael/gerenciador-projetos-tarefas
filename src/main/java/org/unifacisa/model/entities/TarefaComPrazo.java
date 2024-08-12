package org.unifacisa.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TarefaComPrazo extends Tarefa{
    private LocalDate prazo;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

}
