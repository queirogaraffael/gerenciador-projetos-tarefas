package org.unifacisa.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private boolean emAberto;
    private int prioridade;

    protected Tarefa() {
    }

    protected Tarefa(Long id, String descricao, boolean emAberto, int prioridade) {
        this.id = id;
        this.descricao = descricao;
        this.emAberto = emAberto;
        this.prioridade = prioridade;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isEmAberto() {
        return emAberto;
    }

    public void setEmAberto(boolean emAberto) {
        this.emAberto = emAberto;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(id, tarefa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void executar(){
        emAberto = false;
    }

}
