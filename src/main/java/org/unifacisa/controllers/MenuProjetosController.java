package org.unifacisa.controllers;

import org.unifacisa.commons.constantes.ConstantesMenuProjetosController;
import org.unifacisa.commons.utils.SelecionaIdDTO;
import org.unifacisa.DTOs.ProjetoDTO;
import org.unifacisa.model.domain.entities.Projeto;
import org.unifacisa.services.ProjetoService;
import org.unifacisa.view.ProjetosControllerView;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.util.List;

public class MenuProjetosController {
    private final ProjetoService projetoService;

    public MenuProjetosController(EntityManagerFactory entityManagerFactory) {
        this.projetoService = new ProjetoService(entityManagerFactory);
    }


    public void menuGerenciadorProjetos() {
        String opcaoMenuGerenciadoProjetos;

        do {
            opcaoMenuGerenciadoProjetos = ProjetosControllerView.exibirMenuProjetosView();

            switch (opcaoMenuGerenciadoProjetos) {

                case (ConstantesMenuProjetosController.CRIAR_PROJETO):
                    criaProjeto();
                    break;

                case (ConstantesMenuProjetosController.ATUALIZAR_PROJETO):
                    atualizaProjeto();
                    break;

                case (ConstantesMenuProjetosController.VISUALIZAR_PROJETOS):
                    visualizaProjetos();
                    break;

                case (ConstantesMenuProjetosController.BUSCA_VISUALIZA_PROJETOS_POR_NOME):
                    visualizaProjetosPorNome();
                    break;

                case (ConstantesMenuProjetosController.REMOVER_PROJETO):
                    removeProjeto();
                    break;

                default:
                    break;

            }


        } while (!opcaoMenuGerenciadoProjetos.equals(ConstantesMenuProjetosController.VOLTAR));
    }


    public void criaProjeto() {
        String titulo = JOptionPane.showInputDialog("Digite o título: ");

        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O título não pode ser vazio.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean verificaSeNomeJaExiste = projetoService.verificaSeHaProjetoComMesmoTitulo(titulo.trim());

        if (verificaSeNomeJaExiste) {
            JOptionPane.showMessageDialog(null, "Projeto com o mesmo titulo ja existe. Tente com um outro titulo.", "Alerta", JOptionPane.ERROR_MESSAGE);
        } else {
            String descricao = JOptionPane.showInputDialog("Digite uma descricao: ");

            if (descricao == null || descricao.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "A descricao nao pode ser vazia.", "Alerta", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Projeto novoProjeto = new Projeto();
            novoProjeto.setTitulo(titulo.trim());
            novoProjeto.setDescricao(descricao.trim());
            novoProjeto.setEmAberto(true);

            projetoService.criarProjeto(novoProjeto);
            JOptionPane.showMessageDialog(null, "Projeto criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void atualizaProjeto() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta"
                    , JOptionPane.ERROR_MESSAGE);
        } else {
            Long id = SelecionaIdDTO.selecionaProjetoDTO(projetos);
            Projeto projeto = projetoService.getProjetoById(id);
            exibirOpcoesDeModificacaoProjetoEModifica(projeto);
        }


    }

    public void visualizaProjetos() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta"
                    , JOptionPane.ERROR_MESSAGE);
        } else {
            selecionaEExibeProjeto(projetos);
        }

    }

    public void visualizaProjetosPorNome() {
        String nome = JOptionPane.showInputDialog("Digite o nome: ");
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTOPorNome(nome);


        if (projetos.isEmpty()) {


            JOptionPane.showMessageDialog(null, "Sem projeto(s) correspondentes.", "Alerta"
                    , JOptionPane.ERROR_MESSAGE);

        } else {
            selecionaEExibeProjeto(projetos);
        }


    }

    public void removeProjeto() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta"
                    , JOptionPane.ERROR_MESSAGE);
        } else {
            Long id = SelecionaIdDTO.selecionaProjetoDTO(projetos);
            projetoService.deletarProjeto(id);
            JOptionPane.showMessageDialog(null, "Projeto removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

    }


    public void selecionaEExibeProjeto(List<ProjetoDTO> projetos) {
        Long id = SelecionaIdDTO.selecionaProjetoDTO(projetos);
        Projeto projeto = projetoService.getProjetoById(id);
        JOptionPane.showMessageDialog(null, projeto.toString(), "Projeto: ", JOptionPane.ERROR_MESSAGE);
    }

    public void exibirOpcoesDeModificacaoProjetoEModifica(Projeto projeto) {
        int opcao;
        Object[] opcoes = {"Titulo", "Descricao", "Status", "Todos", "Voltar"};

        do {
            opcao = JOptionPane.showOptionDialog(null, "Escolha uma opcao:", "Modificar Projeto",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcoes, opcoes[0]);

            switch (opcao) {
                case 0:
                    atualizarTitulo(projeto);
                    break;

                case 1:
                    atualizarDescricao(projeto);
                    break;

                case 2:
                    atualizarStatus(projeto);
                    break;

                case 3:
                    atualizarTitulo(projeto);
                    atualizarDescricao(projeto);
                    atualizarStatus(projeto);
                    break;

                default:
                    break;
            }

            if (opcao != 4){
                projetoService.atualizarProjeto(projeto);
                JOptionPane.showMessageDialog(null, "Projeto modificado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }


        } while (opcao != 4);
    }

    private void atualizarTitulo(Projeto projeto) {
        String novoTitulo;
        while (true) {
            novoTitulo = JOptionPane.showInputDialog("Digite o novo titulo: ");
            if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
                projeto.setTitulo(novoTitulo);
                return;
            }
            JOptionPane.showMessageDialog(null, "O titulo nao pode ser vazio. Digite um novo título!", "Alerta", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void atualizarDescricao(Projeto projeto) {
        String novaDescricao;
        while (true) {
            novaDescricao = JOptionPane.showInputDialog("Digite a nova descrição: ");
            if (novaDescricao != null && !novaDescricao.trim().isEmpty()) {
                projeto.setDescricao(novaDescricao);
                return;
            }
            JOptionPane.showMessageDialog(null, "A descricao nao pode ser vazia. Digite uma nova descricao!", "Alerta", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void atualizarStatus(Projeto projeto) {
        Object[] opcoesStatus = {"Em aberto", "Concluido"};
        int opcaoStatus = JOptionPane.showOptionDialog(null, "Escolha o status:", "Modificar Status",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoesStatus, opcoesStatus[0]);

        if (opcaoStatus == 0) {
            projeto.setEmAberto(true);
        } else if (opcaoStatus == 1) {
            projeto.setEmAberto(false);
        }
    }


}
