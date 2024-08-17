package org.unifacisa.model.dao;

import org.unifacisa.DTOs.TarefaDTO;
import org.unifacisa.model.domain.entities.TarefaComPrazo;

import java.util.List;

public interface TarefaComPrazoDao {
    void criaTarefa(TarefaComPrazo tarefa);

    TarefaComPrazo getTarefaById(Long id);

    boolean verificaSeHaTarefaComMesmoTitulo(String titulo);

    List<TarefaDTO> getTarefasDTODeUmProjeto(Long idProjeto);

    List<TarefaDTO> getTarefasDTOByPrioridade(int prioridade);

    List<TarefaDTO> getTarefasDTOByStatus(boolean emAberto, Long idProjeto);

    void atualizaTarefa(TarefaComPrazo tarefa);

    void deletaTarefaById(Long id);

    void executaTarefaById(Long id);

}
