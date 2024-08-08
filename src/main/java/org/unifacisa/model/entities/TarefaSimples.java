package org.unifacisa.model.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TarefaSimples extends Tarefa {

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;


    public TarefaSimples(Projeto projeto) {
        this.projeto = projeto;
    }

    public TarefaSimples(Long id, String descricao, boolean emAberto, int prioridade, Projeto projeto) {
        super(id, descricao, emAberto, prioridade);
        this.projeto = projeto;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
}
