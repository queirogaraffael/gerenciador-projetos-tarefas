package org.unifacisa.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private boolean emAberto;

    @OneToMany(mappedBy = "projeto")
    Set<TarefaSimples> tarefasSimples = new HashSet<>();

    @OneToMany(mappedBy = "projeto")
    Set<TarefaComPrazo> tarefasComPrazo = new HashSet<>();

    public Projeto() {
    }

    public Projeto(Long id, String descricao, boolean emAberto, Set<TarefaSimples> tarefasSimples, Set<TarefaComPrazo> tarefasComPrazo) {
        this.id = id;
        this.descricao = descricao;
        this.emAberto = emAberto;
        this.tarefasSimples = tarefasSimples;
        this.tarefasComPrazo = tarefasComPrazo;
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

    public Set<TarefaSimples> getTarefasSimples() {
        return tarefasSimples;
    }

    public void setTarefasSimples(Set<TarefaSimples> tarefasSimples) {
        this.tarefasSimples = tarefasSimples;
    }

    public Set<TarefaComPrazo> getTarefasComPrazo() {
        return tarefasComPrazo;
    }

    public void setTarefasComPrazo(Set<TarefaComPrazo> tarefasComPrazo) {
        this.tarefasComPrazo = tarefasComPrazo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projeto projeto = (Projeto) o;
        return Objects.equals(id, projeto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
