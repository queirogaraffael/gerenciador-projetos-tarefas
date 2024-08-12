package org.unifacisa.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String titulo;
    private String descricao;
    private boolean emAberto;

    @OneToMany(mappedBy = "projeto")
    Set<TarefaSimples> tarefasSimples = new HashSet<>();

    @OneToMany(mappedBy = "projeto")
    Set<TarefaComPrazo> tarefasComPrazo = new HashSet<>();

}
