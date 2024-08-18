package com.unifacisa.commons.utils;

import com.unifacisa.dtos.ProjetoDTO;
import com.unifacisa.dtos.TarefaDTO;
import com.unifacisa.exceptions.GlobalExceptionHandler;
import com.unifacisa.view.ProjetosViews;
import com.unifacisa.view.TarefasViews;

import java.util.List;

public class SelecionaDTO {

    private SelecionaDTO() {
    }

    public static Long selecionaProjetoDTO(List<ProjetoDTO> projetos) {
        Object[] opcoes = converterProjetosParaArray(projetos);
        String projetoSelecionado = ProjetosViews.exibirProjetosDTOsView(opcoes);


        ProjetoDTO projeto = buscarProjetoPorTitulo(projetos, projetoSelecionado);

        if (projeto != null) {
            return projeto.getId();
        } else {
            GlobalExceptionHandler.handleNoResultException("Projeto não encontrado.");
            return null;
        }
    }


    public static Long selecionaTarefaDTO(List<TarefaDTO> tarefas) {
        Object[] opcoesTarefas = converterTarefasParaArray(tarefas);
        String tarefaSelecionado = TarefasViews.exibirTarefasDTOsView(opcoesTarefas);


        TarefaDTO tarefa = buscarTarefaPorTitulo(tarefas, tarefaSelecionado);

        if (tarefa != null) {
            return tarefa.getId();
        } else {
            GlobalExceptionHandler.handleNoResultException("Tarefa nao encontrada.");
            return null;
        }
    }


    private static Object[] converterProjetosParaArray(List<ProjetoDTO> projetos) {
        return projetos.stream()
                .map(ProjetoDTO::toString)
                .toArray(Object[]::new);
    }

    private static Object[] converterTarefasParaArray(List<TarefaDTO> tarefas) {
        return tarefas.stream()
                .map(TarefaDTO::toString)
                .toArray(Object[]::new);
    }


    public static ProjetoDTO buscarProjetoPorTitulo(List<ProjetoDTO> projetos, String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return null;
        }

        for (ProjetoDTO projeto : projetos) {
            if (projeto != null && projeto.getTitulo() != null && projeto.getTitulo().equalsIgnoreCase(titulo.trim())) {
                return projeto;
            }
        }
        return null;
    }

    public static TarefaDTO buscarTarefaPorTitulo(List<TarefaDTO> tarefas, String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return null;
        }

        for (TarefaDTO tarefa : tarefas) {
            if (tarefa != null && tarefa.getTitulo() != null && tarefa.getTitulo().equalsIgnoreCase(titulo.trim())) {
                return tarefa;
            }
        }
        return null;
    }


}

