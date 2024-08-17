package org.unifacisa.model.dao;

import org.unifacisa.DTOs.TarefaDTO;
import org.unifacisa.model.domain.entities.TarefaSimples;

import java.util.List;

public interface TarefaSimplesDao {
    void criaTarefa(TarefaSimples tarefa);

    TarefaSimples getTarefaById(Long id);

    boolean verificaSeHaTarefaComMesmoTitulo(String titulo);

    List<TarefaDTO> getTarefasDTODeUmProjeto(Long idProjeto);

    List<TarefaDTO> getTarefasDTOByPrioridade(int prioridade);

    List<TarefaDTO> getTarefasDTOByStatus(boolean emAberto, Long idProjeto);

    void atualizaTarefa(TarefaSimples tarefa);

    void deletaTarefaById(Long id);

    void executaTarefaById(Long id);

}
