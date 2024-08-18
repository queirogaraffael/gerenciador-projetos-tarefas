package com.unifacisa.model.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TarefaSimples extends Tarefa {

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    @Override
    public String toString() {
        return "Tarefa: \n" +
                "Titulo: " + getTitulo() +
                "\n Descricao: " + getDescricao() +
                "\n Em aberto: " + (isEmAberto() ? "Sim" : "Nao");
    }

}
