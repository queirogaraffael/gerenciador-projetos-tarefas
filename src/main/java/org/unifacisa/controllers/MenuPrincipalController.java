package org.unifacisa.controllers;


import org.unifacisa.commons.constantes.ConstantesMenuPrincipal;
import org.unifacisa.hibernate_connection.EntityManagerFactoryService;

import javax.swing.*;

public class MenuPrincipalController {

    private EntityManagerFactoryService entityManagerFactoryService = new EntityManagerFactoryService();


    public MenuPrincipalController() {

    }


    public void exibirMenuPrincipal() {
        entityManagerFactoryService.inicializarEntityManagerFactory();

        int opcaoMenuPrincipal;
        Object[] opcoes = {"Gerenciador de Projetos", "Gerenciador de Tarefas", "Encerrar programa"};


        do {
            opcaoMenuPrincipal = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Menu Principal", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

            switch (opcaoMenuPrincipal) {

                case ConstantesMenuPrincipal.GERENCIADOR_PROJETOS:

                    break;

                case ConstantesMenuPrincipal.GERENCIADOR_TAREFAS:

                    break;

                default:
                    entityManagerFactoryService.fechaEntityManagerFactory();
                    break;

            }
        } while (opcaoMenuPrincipal != ConstantesMenuPrincipal.SAIR);

    }
}