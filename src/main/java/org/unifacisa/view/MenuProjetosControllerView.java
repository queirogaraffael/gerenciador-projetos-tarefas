package org.unifacisa.view;

import javax.swing.JOptionPane;

public class MenuProjetosControllerView {

    private static final String MENU_TITLE = "Gerenciador de Projeto";
    private static final String MENU_PROMPT = "Escolha uma opcao";

    private static final Object[] MENU_OPTIONS = {
            "Criar Projeto",
            "Atualizar Projeto",
            "Visualizar Projeto(s)",
            "Buscar e Visualizar Projeto(s) por Nome",
            "Remover Projeto",
            "Voltar"
    };

    public static String exibirMenuProjetosView() {
        Object opcaoSelecionada = JOptionPane.showInputDialog(
                null,
                MENU_PROMPT,
                MENU_TITLE,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                MENU_OPTIONS,
                MENU_OPTIONS[0]
        );

        return opcaoSelecionada.toString();
    }
}
