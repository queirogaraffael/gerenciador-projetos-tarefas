package com.unifacisa.controllers;

import com.unifacisa.commons.constantes.ConstantesMenuProjetosController;
import com.unifacisa.commons.utils.SelecionaDTO;
import com.unifacisa.services.TarefaComPrazoService;
import com.unifacisa.services.TarefaSimplesService;
import com.unifacisa.view.ProjetosViews;
import com.unifacisa.dtos.ProjetoDTO;
import com.unifacisa.model.domain.entities.Projeto;
import com.unifacisa.services.ProjetoService;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MenuProjetosController {

    private final ProjetoService projetoService;
    private final TarefaSimplesService tarefaSimplesService;
    private final TarefaComPrazoService tarefaComPrazoService;

    public MenuProjetosController(EntityManagerFactory entityManagerFactory) {
        this.projetoService = new ProjetoService(entityManagerFactory);
        this.tarefaSimplesService = new TarefaSimplesService(entityManagerFactory);
        this.tarefaComPrazoService = new TarefaComPrazoService(entityManagerFactory);
    }


    public void menuGerenciadorProjetos() {
        String opcaoMenuGerenciadoProjetos;

        do {
            opcaoMenuGerenciadoProjetos = ProjetosViews.exibirMenuProjetosView();

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
        String titulo = ProjetosViews.leTituloProjeto();

        if (titulo == null || titulo.trim().isEmpty()) {
            ProjetosViews.exibirAlertaTituloNaoPodeSerVazio();
            return;
        }

        boolean verificaSeNomeJaExiste = projetoService.verificaSeHaProjetoComMesmoTitulo(titulo.trim());

        if (verificaSeNomeJaExiste) {
            ProjetosViews.exibirAlertaProjetoComMesmoTitulo();
            return;
        }

        String descricao = ProjetosViews.leDescricaoProjeto();

        if (descricao == null || descricao.trim().isEmpty()) {
            ProjetosViews.exibirAlertaDescricaoNaoPodeSerVazia();
            return;
        }

        Projeto novoProjeto = new Projeto();
        novoProjeto.setTitulo(titulo.trim());
        novoProjeto.setDescricao(descricao.trim());
        novoProjeto.setEmAberto(true);

        projetoService.criarProjeto(novoProjeto);
        ProjetosViews.exibirAlertaProjetoFoiCriadoComSucesso();

    }

    public void atualizaProjeto() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            ProjetosViews.exibirAlertaSemProjeto();
            return;
        }
        Long id = SelecionaDTO.selecionaProjetoDTO(projetos);
        Projeto projeto = projetoService.getProjetoById(id);
        exibirOpcoesDeModificacaoProjetoEModifica(projeto);


    }

    public void visualizaProjetos() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            ProjetosViews.exibirAlertaSemProjeto();
            return;
        }

        selecionaEExibeProjeto(projetos);

    }

    public void visualizaProjetosPorNome() {
        String nome = ProjetosViews.leTituloProjeto();
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTOPorNome(nome);

        if (projetos.isEmpty()) {
            ProjetosViews.exibirAlertaSemProjetosCorrespondentes();
            return;
        }

        selecionaEExibeProjeto(projetos);

    }

    public void removeProjeto() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();


        if (projetos.isEmpty()) {
            ProjetosViews.exibirAlertaSemProjeto();

            return;
        }

        Long id = SelecionaDTO.selecionaProjetoDTO(projetos);
        projetoService.deletarProjeto(id);
        ProjetosViews.exibirAlertaProjetoRemovidoComSucesso();

    }

    private void executarProjeto() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            ProjetosViews.exibirAlertaSemProjeto();
            return;
        }

        Long idProjeto = SelecionaDTO.selecionaProjetoDTO(projetos);

        tarefaSimplesService.executaTarefasPorProjeto(idProjeto);
        tarefaComPrazoService.executaTarefasPorProjeto(idProjeto);

        ProjetosViews.exibirAlertaProjetoExecutadoComSucesso();

    }


    public void selecionaEExibeProjeto(List<ProjetoDTO> projetos) {
        Long id = SelecionaDTO.selecionaProjetoDTO(projetos);
        Projeto projeto = projetoService.getProjetoById(id);
        ProjetosViews.exibeDadosProjetos(projeto);
    }

    public void exibirOpcoesDeModificacaoProjetoEModifica(Projeto projeto) {
        int opcao;

        do {
            opcao = ProjetosViews.selecionaOpcaoDeModificacaoProjeto();

            switch (opcao) {
                case 0:
                    ProjetosViews.atualizarTitulo(projeto);
                    break;

                case 1:
                    ProjetosViews.atualizarDescricao(projeto);
                    break;

                case 2:
                    ProjetosViews.atualizarTitulo(projeto);
                    ProjetosViews.atualizarDescricao(projeto);
                    break;

                default:
                    break;
            }

            if (opcao != 3) {
                projetoService.atualizarProjeto(projeto);
                ProjetosViews.exibirAlertaProjetoModificadoComSucesso();
            }


        } while (opcao != 3);
    }


}
