package org.unifacisa.model.dao;

import org.unifacisa.model.DTOs.TarefaDTO;
import org.unifacisa.model.entities.TarefaComPrazo;

import java.util.List;

public interface TarefaComPrazoDao {
    void criaTarefa(TarefaComPrazo tarefa);

    TarefaComPrazo getTarefaById(Long id);

    List<TarefaDTO> getTarefasDTO();

    List<TarefaDTO> getTarefasDTOByPrioridade(int prioridade);

    List<TarefaDTO> getTarefasDTOByStatus(boolean emAberto);

    void atualizaTarefa(TarefaComPrazo tarefa);

    void deletaTarefaById(Long id);

    void executaTarefaById(Long id);

}
