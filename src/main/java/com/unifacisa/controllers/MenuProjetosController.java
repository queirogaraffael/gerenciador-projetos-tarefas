package com.unifacisa.controllers;

import com.unifacisa.commons.constantes.ConstantesMenuProjetosController;
import com.unifacisa.commons.utils.SelecionaDTO;
import com.unifacisa.dtos.ProjetoDTO;
import com.unifacisa.model.domain.entities.Projeto;
import com.unifacisa.services.ProjetoService;
import com.unifacisa.services.TarefaComPrazoService;
import com.unifacisa.services.TarefaSimplesService;
import com.unifacisa.view.ProjetosViews;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MenuProjetosController {

    private final ProjetoService projetoService;
    private final TarefaSimplesService tarefaSimplesService;
    private final TarefaComPrazoService tarefaComPrazoService;
    private final ProjetosViews projetosViews;

    public MenuProjetosController(EntityManagerFactory entityManagerFactory) {
        this.projetoService = new ProjetoService(entityManagerFactory);
        this.tarefaSimplesService = new TarefaSimplesService(entityManagerFactory);
        this.tarefaComPrazoService = new TarefaComPrazoService(entityManagerFactory);
        this.projetosViews = new ProjetosViews();
    }


    public void menuGerenciadorProjetos() {
        String opcaoMenuGerenciadoProjetos;

        do {
            opcaoMenuGerenciadoProjetos = projetosViews.exibirMenuProjetosView();

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

                case (ConstantesMenuProjetosController.EXECUTAR_PROJETO):
                    executarProjeto();
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
        String titulo = projetosViews.leTituloProjeto();

        if (titulo == null || titulo.trim().isEmpty()) {
            projetosViews.exibirAlertaTituloNaoPodeSerVazio();
            return;
        }

        boolean verificaSeNomeJaExiste = projetoService.verificaSeHaProjetoComMesmoTitulo(titulo.trim());

        if (verificaSeNomeJaExiste) {
            projetosViews.exibirAlertaProjetoComMesmoTitulo();
            return;
        }

        String descricao = projetosViews.leDescricaoProjeto();

        if (descricao == null || descricao.trim().isEmpty()) {
            projetosViews.exibirAlertaDescricaoNaoPodeSerVazia();
            return;
        }

        Projeto novoProjeto = new Projeto();
        novoProjeto.setTitulo(titulo.trim());
        novoProjeto.setDescricao(descricao.trim());
        novoProjeto.setEmAberto(true);

        projetoService.criarProjeto(novoProjeto);
        projetosViews.exibirAlertaProjetoFoiCriadoComSucesso();

    }

    public void atualizaProjeto() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            projetosViews.exibirAlertaSemProjeto();
            return;
        }
        Long id = SelecionaDTO.selecionaProjetoDTO(projetos);
        Projeto projeto = projetoService.getProjetoById(id);
        exibirOpcoesDeModificacaoProjetoEModifica(projeto);


    }

    public void visualizaProjetos() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            projetosViews.exibirAlertaSemProjeto();
            return;
        }

        selecionaEExibeProjeto(projetos);

    }

    public void visualizaProjetosPorNome() {
        String nome = projetosViews.leTituloProjeto();
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTOPorNome(nome);

        if (projetos.isEmpty()) {
            projetosViews.exibirAlertaSemProjetosCorrespondentes();
            return;
        }

        selecionaEExibeProjeto(projetos);

    }

    public void removeProjeto() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();


        if (projetos.isEmpty()) {
            projetosViews.exibirAlertaSemProjeto();

            return;
        }

        Long id = SelecionaDTO.selecionaProjetoDTO(projetos);
        projetoService.deletarProjeto(id);
        projetosViews.exibirAlertaProjetoRemovidoComSucesso();

    }

    private void executarProjeto() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            projetosViews.exibirAlertaSemProjeto();
            return;
        }

        Long idProjeto = SelecionaDTO.selecionaProjetoDTO(projetos);

        tarefaSimplesService.executaTarefasPorProjeto(idProjeto);
        tarefaComPrazoService.executaTarefasPorProjeto(idProjeto);

        projetosViews.exibirAlertaProjetoExecutadoComSucesso();

    }


    public void selecionaEExibeProjeto(List<ProjetoDTO> projetos) {
        Long id = SelecionaDTO.selecionaProjetoDTO(projetos);
        Projeto projeto = projetoService.getProjetoById(id);
        projetosViews.exibeDadosProjetos(projeto);
    }

    public void exibirOpcoesDeModificacaoProjetoEModifica(Projeto projeto) {
        int opcao;

        do {
            opcao = projetosViews.selecionaOpcaoDeModificacaoProjeto();

            switch (opcao) {
                case 0:
                    projetosViews.atualizarTitulo(projeto);
                    break;

                case 1:
                    projetosViews.atualizarDescricao(projeto);
                    break;

                case 2:
                    projetosViews.atualizarTitulo(projeto);
                    projetosViews.atualizarDescricao(projeto);
                    break;

                default:
                    break;
            }

            if (opcao != 3) {
                projetoService.atualizarProjeto(projeto);
                projetosViews.exibirAlertaProjetoModificadoComSucesso();
            }


        } while (opcao != 3);
    }


}
