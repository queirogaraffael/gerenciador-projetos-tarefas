package org.unifacisa.commons.utils;

import org.unifacisa.DTOs.TarefaDTO;
import org.unifacisa.exceptions.GlobalExceptionHandler;
import org.unifacisa.DTOs.ProjetoDTO;

import javax.swing.*;
import java.util.List;

public class SelecionaIdDTO {

    private SelecionaIdDTO() {
    }

    public static Long selecionaProjetoDTO(List<ProjetoDTO> projetos) {
        Object[] opcoes = converterProjetosParaArray(projetos);
        String projetoSelecionado = exibirProjetosDTOsView(opcoes);


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
        String tarefaSelecionado = exibirTarefasDTOsView(opcoesTarefas);


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

    private static String exibirTarefasDTOsView(Object[] options) {
        Object opcaoSelecionada = JOptionPane.showInputDialog(
                null,
                "Escolha uma tarefa: ",
                "Tarefas",
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (opcaoSelecionada != null) {
            return opcaoSelecionada.toString();
        } else {
            GlobalExceptionHandler.handleGeneralException("Nenhuma opcao foi selecionada.");
            return null;
        }
    }


    private static String exibirProjetosDTOsView(Object[] options) {
        Object opcaoSelecionada = JOptionPane.showInputDialog(
                null,
                "Escolha um projeto: ",
                "Projetos",
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (opcaoSelecionada != null) {
            return opcaoSelecionada.toString();
        } else {
            GlobalExceptionHandler.handleGeneralException("Nenhum projeto foi selecionado.");
            return null;
        }
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

