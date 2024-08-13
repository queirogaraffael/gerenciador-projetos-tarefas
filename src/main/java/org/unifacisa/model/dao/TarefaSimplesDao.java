package org.unifacisa.model.dao;

import org.unifacisa.model.DTOs.TarefaDTO;
import org.unifacisa.model.entities.TarefaSimples;

import java.util.List;

public interface TarefaSimplesDao {
    void criaTarefa(TarefaSimples tarefa);

    TarefaSimples getTarefaById(Long id);

    List<TarefaDTO> getTarefasDTO();

    List<TarefaDTO> getTarefasDTOByPrioridade(int prioridade);

    List<TarefaDTO> getTarefasDTOByStatus(boolean emAberto);

    void atualizaTarefa(TarefaSimples tarefa);

    void deletaTarefaById(Long id);

    void executaTarefaById(Long id);

}
