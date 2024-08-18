package com.unifacisa.model.dao;

import com.unifacisa.dtos.TarefaDTO;
import com.unifacisa.enums.Prioridade;
import com.unifacisa.model.domain.entities.TarefaComPrazo;

import java.util.List;

public interface TarefaComPrazoDao {
    void criaTarefa(TarefaComPrazo tarefa);

    TarefaComPrazo getTarefaById(Long id);

    boolean verificaSeHaTarefaComMesmoTitulo(String titulo);

    List<TarefaDTO> getTarefasDTODeUmProjeto(Long idProjeto);

    List<TarefaDTO> getTarefasDTODeUmProjetoByPrioridade(Prioridade prioridade, Long idProjeto);

    List<TarefaDTO> getTarefasDTOByStatus(boolean emAberto, Long idProjeto);

    void atualizaTarefa(TarefaComPrazo tarefa);

    void deletaTarefaById(Long id);

    void executaTarefaById(Long id);

    void executaTarefasPorProjeto(Long idProjeto);
}
