package org.unifacisa.controllers;

import org.unifacisa.commons.constantes.ConstantesMenuProjetosController;
import org.unifacisa.view.MenuProjetosView;

public class MenuProjetosController {

    public MenuProjetosController() {
    }


    public void MenuGerenciadorProjetos() {
        String opcaoMenuGerenciadoProjetos;

        do {
                opcaoMenuGerenciadoProjetos = MenuProjetosView.exibirMenuProjetosView();

                switch (opcaoMenuGerenciadoProjetos) {

                    case (ConstantesMenuProjetosController.CRIAR_PROJETO):
                        break;

                    case (ConstantesMenuProjetosController.ATUALIZAR_PROJETO):
                        break;

                    case (ConstantesMenuProjetosController.REMOVER_PROJETO):
                        break;

                    case (ConstantesMenuProjetosController.LISTAR_PROJETOS):
                        break;

                    case (ConstantesMenuProjetosController.BUSCA_PROJETOS_POR_NOME):
                        break;

                    default:
                        break;

                }


        } while (!opcaoMenuGerenciadoProjetos.equals(ConstantesMenuProjetosController.VOLTAR));
    }
}
