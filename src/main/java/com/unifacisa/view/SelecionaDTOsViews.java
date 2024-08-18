package com.unifacisa.view;

import com.unifacisa.exceptions.GlobalExceptionHandler;

import javax.swing.*;

public class SelecionaDTOsViews {


    public static String exibirTarefasDTOsView(Object[] options) {
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

    public static String exibirProjetosDTOsView(Object[] options) {
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
}
