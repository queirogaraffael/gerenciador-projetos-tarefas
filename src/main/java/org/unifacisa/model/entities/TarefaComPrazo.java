package org.unifacisa.model.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class TarefaComPrazo extends Tarefa{
    private LocalDate prazo;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;


    public TarefaComPrazo() {
    }


    public TarefaComPrazo(Long id, String descricao, boolean emAberto, int prioridade, LocalDate prazo, Projeto projeto) {
        super(id, descricao, emAberto, prioridade);
        this.prazo = prazo;
        this.projeto = projeto;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
}
