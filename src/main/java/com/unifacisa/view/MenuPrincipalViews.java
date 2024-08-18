package com.unifacisa.view;

import javax.swing.*;

public class MenuPrincipalViews {

    private MenuPrincipalViews() {
    }

    public static int exibeViewEscolhaStatusTarefa() {
        Object[] opcoes = {"Gerenciador de Projetos", "Gerenciador de Tarefas", "Encerrar programa"};
        return JOptionPane.showOptionDialog(
                null,
                "Escolha uma opcao:",
                "Menu Principal",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

    }
}
