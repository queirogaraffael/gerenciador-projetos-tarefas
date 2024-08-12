package org.unifacisa.view;

import javax.swing.JOptionPane;

public class MenuProjetosControllerView {

    private static final String MENU_TITLE = "Fluxo De Caixa";
    private static final String MENU_PROMPT = "Escolha uma opção";

    private static final Object[] MENU_OPTIONS = {
            "Criar Projeto",
            "Atualizar Projeto",
            "Remover Projeto",
            "Listar todos os Projetos",
            "Buscar Projeto(s) por Nome",
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

        return opcaoSelecionada != null ? opcaoSelecionada.toString() : "";
    }
}
