package com.unifacisa.controllers;


import com.unifacisa.commons.constantes.ConstantesMenuPrincipalController;
import com.unifacisa.hibernate_connection.EntityManagerFactoryService;
import com.unifacisa.view.MenuPrincipalViews;

public class MenuPrincipalController {

    private final EntityManagerFactoryService entityManagerFactoryService = new EntityManagerFactoryService();
    private final MenuProjetosController menuProjetosController = new MenuProjetosController(entityManagerFactoryService.entityManagerFactory());
    private final MenuTarefasController menuTarefasController = new MenuTarefasController(entityManagerFactoryService.entityManagerFactory());


    public MenuPrincipalController() {
        entityManagerFactoryService.inicializarEntityManagerFactory();
    }


    public void exibirMenuPrincipal() {

        int opcaoMenuPrincipal;

        try {
            do {
                opcaoMenuPrincipal = MenuPrincipalViews.exibeViewEscolhaStatusTarefa();

                switch (opcaoMenuPrincipal) {

                    case ConstantesMenuPrincipalController.GERENCIADOR_PROJETOS:
                        menuProjetosController.menuGerenciadorProjetos();
                        break;

                    case ConstantesMenuPrincipalController.GERENCIADOR_TAREFAS:
                        menuTarefasController.menuGerenciadorTarefas();
                        break;

                    default:
                        break;

                }
            } while (opcaoMenuPrincipal != ConstantesMenuPrincipalController.SAIR);
        } finally {
            entityManagerFactoryService.fechaEntityManagerFactory();
        }


    }
}