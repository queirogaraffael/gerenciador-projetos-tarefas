package org.unifacisa.controllers;


import org.unifacisa.commons.constantes.ConstantesMenuPrincipalController;
import org.unifacisa.hibernate_connection.EntityManagerFactoryService;

import javax.swing.*;

public class MenuPrincipalController {

    private final EntityManagerFactoryService entityManagerFactoryService = new EntityManagerFactoryService();
    private final MenuProjetosController menuProjetosController;

    public MenuPrincipalController() {
        this.menuProjetosController = new MenuProjetosController();
    }


    public void exibirMenuPrincipal() {
        entityManagerFactoryService.inicializarEntityManagerFactory();

        int opcaoMenuPrincipal;
        Object[] opcoes = {"Gerenciador de Projetos", "Gerenciador de Tarefas", "Encerrar programa"};


        do {
            opcaoMenuPrincipal = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Menu Principal", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

            switch (opcaoMenuPrincipal) {

                case ConstantesMenuPrincipalController.GERENCIADOR_PROJETOS:
                    menuProjetosController.MenuGerenciadorProjetos();
                    break;

                case ConstantesMenuPrincipalController.GERENCIADOR_TAREFAS:

                    break;

                default:
                    entityManagerFactoryService.fechaEntityManagerFactory();
                    break;

            }
        } while (opcaoMenuPrincipal != ConstantesMenuPrincipalController.SAIR);

    }
}