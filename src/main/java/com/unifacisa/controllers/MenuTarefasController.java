package com.unifacisa.controllers;

import com.unifacisa.commons.constantes.ConstantesMenuTarefasController;
import com.unifacisa.commons.utils.ManipulaData;
import com.unifacisa.commons.utils.SelecionaDTO;
import com.unifacisa.dtos.ProjetoDTO;
import com.unifacisa.dtos.TarefaDTO;
import com.unifacisa.enums.Prioridade;
import com.unifacisa.model.domain.entities.Projeto;
import com.unifacisa.model.domain.entities.Tarefa;
import com.unifacisa.model.domain.entities.TarefaComPrazo;
import com.unifacisa.model.domain.entities.TarefaSimples;
import com.unifacisa.services.ProjetoService;
import com.unifacisa.services.TarefaComPrazoService;
import com.unifacisa.services.TarefaSimplesService;
import com.unifacisa.view.ProjetosViews;
import com.unifacisa.view.TarefasViews;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MenuTarefasController {

    private final ProjetoService projetoService;
    private final TarefaComPrazoService tarefaComPrazoService;
    private final TarefaSimplesService tarefaSimplesService;
    private final TarefasViews tarefasViews;
    private final ProjetosViews projetosViews;

    public MenuTarefasController(EntityManagerFactory entityManagerFactory) {
        this.projetoService = new ProjetoService(entityManagerFactory);
        this.tarefaComPrazoService = new TarefaComPrazoService(entityManagerFactory);
        this.tarefaSimplesService = new TarefaSimplesService(entityManagerFactory);

        this.tarefasViews = new TarefasViews();
        this.projetosViews = new ProjetosViews();

    }

    public void menuGerenciadorTarefas() {
        String opcaoMenuGerenciadoTarefas;

        do {
            opcaoMenuGerenciadoTarefas = tarefasViews.exibirMenuTarefasView();

            switch (opcaoMenuGerenciadoTarefas) {

                case (ConstantesMenuTarefasController.CRIA_TAREFA):
                    criarTarefa();
                    break;

                case (ConstantesMenuTarefasController.ATUALIZA_TAREFA_DE_UM_PROJETO):
                    atualizaTarefa();
                    break;

                case (ConstantesMenuTarefasController.VISUALIZAR_TAREFA_POR_PROJETO):
                    visualizarTarefa();
                    break;

                case (ConstantesMenuTarefasController.VISUALIZAR_TAREFA_POR_PRIORIDADE):
                    visualizarTarefasPorPrioridade();
                    break;

                case (ConstantesMenuTarefasController.VISUALIZAR_TAREFA_POR_STATUS):
                    visualizarTarefasPeloStatus();
                    break;

                case (ConstantesMenuTarefasController.REMOVER_TAREFA):
                    deletaTarefa();
                    break;

                case (ConstantesMenuTarefasController.EXECUTAR_TAREFA):
                    executaTarefa();
                    break;

                default:
                    break;

            }


        } while (!opcaoMenuGerenciadoTarefas.equals(ConstantesMenuTarefasController.VOLTAR));
    }


    private void criarTarefa() {

        Long id = selecionarProjetoComVerificacao();

        if (id == null) {
            return;
        }

        Projeto projeto = projetoService.getProjetoById(id);


        String titulo = tarefasViews.leTituloTarefa();


        if (tarefaComPrazoService.verificaSeHaTarefaComMesmoTitulo(titulo) || tarefaSimplesService.verificaSeHaTarefaComMesmoTitulo(titulo)) {
            tarefasViews.exibirAlertaQueNaoPodeTarefaComNomeDuplicadoEmUmProjeto();
            return;
        }


        if (titulo == null || titulo.trim().isEmpty()) {
            tarefasViews.exibirAlertaTituloTarefaNaoPodeSerVazio();
            return;
        }


        String descricao = tarefasViews.leDescricaoTarefa();

        if (descricao == null || descricao.trim().isEmpty()) {
            tarefasViews.exibirAlertaDescricaoTarefaNaoPoderSerVazia();
            return;
        }


        Prioridade prioridade = tarefasViews.selecionarPrioridade();

        if (tarefasViews.desejaAdicionarData()) {
            String dataString = tarefasViews.obterDataValida();
            tarefaComPrazoService.criaTarefaComPrazoESalva(titulo, descricao, projeto, prioridade, dataString);
        } else {
            tarefaSimplesService.criaTarefaSimplesESalva(titulo, descricao, projeto, prioridade);
        }

        tarefasViews.exibirAlertaTarefaCriadaComSucesso();
    }


    private void atualizaTarefa() {
        Long idProjeto = selecionarProjetoComVerificacao();

        if (idProjeto == null) {
            return;
        }

        int tipoTarefa = tarefasViews.exibeViewEscolhaTipoTarefa();

        List<TarefaDTO> tarefas = retornaTarefasDTODeUmProjetoPeloTipo(tipoTarefa, idProjeto);

        if (tarefas == null) {
            tarefasViews.exibirAlertaSemTarefasDoTipoNoProjeto();
            return;
        }

        Long idTarefa = SelecionaDTO.selecionaTarefaDTO(tarefas);

        if (tipoTarefa == 0) {
            TarefaSimples tarefaSimples = tarefaSimplesService.getTarefaById(idTarefa);
            exibiOpcoesDeModificacaoTarefa(tarefaSimples);
        } else {
            TarefaComPrazo tarefaComPrazo = tarefaComPrazoService.getTarefaById(idTarefa);
            exibiOpcoesDeModificacaoTarefa(tarefaComPrazo);
        }
    }


    private void visualizarTarefa() {
        Long idProjeto = selecionarProjetoComVerificacao();

        if (idProjeto == null) {
            return;
        }


        int tipoTarefa = tarefasViews.exibeViewEscolhaTipoTarefa();

        if (tipoTarefa == 0) {
            List<TarefaDTO> tarefasSimples = tarefaSimplesService.buscaTarefasDTODeUmProjeto(idProjeto);
            visualizarTarefaSimples(tarefasSimples);
        } else {
            List<TarefaDTO> tarefasComPrazo = tarefaComPrazoService.buscaTarefasDTODeUmProjeto(idProjeto);
            visualizarTarefaComPrazo(tarefasComPrazo);
        }

    }


    private void visualizarTarefasPorPrioridade() {

        Long idProjeto = selecionarProjetoComVerificacao();

        if (idProjeto == null) {
            return;
        }

        int tipoTarefa = tarefasViews.exibeViewEscolhaTipoTarefa();


        Prioridade tipoPrioridade = tarefasViews.selecionarPrioridade();


        if (tipoTarefa == 0) {
            List<TarefaDTO> tarefas = tarefaSimplesService.getTarefasDTODeUmProjetoByPrioridade(tipoPrioridade, idProjeto);

            visualizarTarefaSimples(tarefas);

        } else {
            List<TarefaDTO> tarefas = tarefaComPrazoService.getTarefasDTODeUmProjetoByPrioridade(tipoPrioridade, idProjeto);

            visualizarTarefaComPrazo(tarefas);

        }


    }


    private void visualizarTarefasPeloStatus() {

        Long idProjeto = selecionarProjetoComVerificacao();

        if (idProjeto == null) {
            return;
        }


        int tipoTarefa = tarefasViews.exibeViewEscolhaTipoTarefa();

        boolean tipoStatus = tarefasViews.exibeViewEscolhaStatusTarefa();

        List<TarefaDTO> tarefas = retornaTarefasDTOPorStatusDeUmProjetoPeloTipo(tipoTarefa, idProjeto, tipoStatus);

        if (tipoTarefa == 0) {
            visualizarTarefaSimples(tarefas);

        } else {
            visualizarTarefaComPrazo(tarefas);

        }

    }


    private void deletaTarefa() {
        Long idProjeto = selecionarProjetoComVerificacao();

        if (idProjeto == null) {
            return;
        }

        int tipoTarefa = tarefasViews.exibeViewEscolhaTipoTarefa();

        List<TarefaDTO> tarefas = retornaTarefasDTODeUmProjetoPeloTipo(tipoTarefa, idProjeto);

        if (tarefas == null || tarefas.isEmpty()) {
            tarefasViews.exibirAlertaSemTarefasDoTipoNoProjeto();
            return;
        }

        Long idTarefa = SelecionaDTO.selecionaTarefaDTO(tarefas);

        if (tipoTarefa == 0) {
            tarefaSimplesService.deletarTarefa(idTarefa);
        } else {
            tarefaComPrazoService.deletarTarefa(idTarefa);
        }

        tarefasViews.exibirAlertaTarefaDeletadaComSucesso();
    }


    private void executaTarefa() {


        Long idProjeto = selecionarProjetoComVerificacao();

        if (idProjeto == null) {
            return;
        }

        int tipoTarefa = tarefasViews.exibeViewEscolhaTipoTarefa();


        List<TarefaDTO> tarefas = retornaTarefasDTOPorStatusDeUmProjetoPeloTipo(tipoTarefa, idProjeto, true);


        if (tarefas == null || tarefas.isEmpty()) {
            tarefasViews.exibirAlertaSemTarefasDoTipoNoProjeto();
            return;
        }

        Long idTarefa = SelecionaDTO.selecionaTarefaDTO(tarefas);

        if (tipoTarefa == 0) {
            tarefaSimplesService.executaTarefa(idTarefa);
        } else {
            tarefaComPrazoService.executaTarefa(idTarefa);
        }

        tarefasViews.exibirAlertaTarefaExecutadaComSucesso();
    }


    private List<TarefaDTO> retornaTarefasDTOPorStatusDeUmProjetoPeloTipo(int tipoTarefa, Long idProjeto, boolean status) {
        if (tipoTarefa == 0) {
            return tarefaSimplesService.buscaTarefasDTOByStatus(status, idProjeto);
        } else {
            return tarefaComPrazoService.buscaTarefasDTOByStatus(status, idProjeto);
        }

    }


    private void visualizarTarefaSimples(List<TarefaDTO> tarefasSimples) {

        if (tarefasSimples == null || tarefasSimples.isEmpty()) {
            tarefasViews.exibirAlertaSemTarefasSimples();
            return;
        }

        Long idTarefa = SelecionaDTO.selecionaTarefaDTO(tarefasSimples);
        TarefaSimples tarefa = tarefaSimplesService.getTarefaById(idTarefa);

        tarefasViews.exibeTarefa(tarefa);
    }

    private void visualizarTarefaComPrazo(List<TarefaDTO> tarefasComPrazo) {

        if (tarefasComPrazo == null || tarefasComPrazo.isEmpty()) {
            tarefasViews.exibirAlertaSemTarefasComPrazo();
            return;
        }

        Long idTarefa = SelecionaDTO.selecionaTarefaDTO(tarefasComPrazo);
        TarefaComPrazo tarefa = tarefaComPrazoService.getTarefaById(idTarefa);
        tarefasViews.exibeTarefa(tarefa);
    }


    private <T extends Tarefa> void exibiOpcoesDeModificacaoTarefa(T tarefa) {
        Object[] opcoes = tarefasViews.obterOpcoesDeModificacao(tarefa);

        int opcao;

        do {
            opcao = tarefasViews.exibirEscolhaModificacao(opcoes);

            switch (opcao) {
                case 0:
                    tarefasViews.atualizarTitulo(tarefa);
                    break;

                case 1:
                    tarefasViews.atualizarDescricao(tarefa);
                    break;

                case 2:
                    tarefasViews.atualizarStatus(tarefa);
                    break;

                case 3:
                    tarefasViews.atualizaPrioridade(tarefa);
                    break;

                case 4:
                    if (tarefa instanceof TarefaComPrazo tarefaComPrazo) {
                        atualizaData(tarefaComPrazo);
                    } else {
                        atualizarTudo(tarefa);
                    }
                    break;

                case 5:
                    if (tarefa instanceof TarefaComPrazo) {
                        atualizarTudo(tarefa);
                    } else {
                        break;
                    }
                    break;

                default:
                    break;
            }

            if (opcao != opcoes.length - 1) {
                atualizarTarefaNoBancoDeDados(tarefa);

                tarefasViews.exibirAlertaTarefaModificadaComSucesso();
            }

        } while (opcao != opcoes.length - 1);
    }


    private <T extends Tarefa> void atualizarTudo(T tarefa) {
        tarefasViews.atualizarTitulo(tarefa);
        tarefasViews.atualizarDescricao(tarefa);
        tarefasViews.atualizarStatus(tarefa);
        tarefasViews.atualizaPrioridade(tarefa);

        if (tarefa instanceof TarefaComPrazo tarefaComPrazo) {
            atualizaData(tarefaComPrazo);
        }

    }


    private <T extends Tarefa> void atualizarTarefaNoBancoDeDados(T tarefa) {
        if (tarefa instanceof TarefaSimples tarefaSimples) {
            tarefaSimplesService.atualizarTarefa(tarefaSimples);
        } else if (tarefa instanceof TarefaComPrazo tarefaComPrazo) {
            tarefaComPrazoService.atualizarTarefa(tarefaComPrazo);
        }
    }


    private void atualizaData(TarefaComPrazo tarefaComPrazo) {
        String dataString = tarefasViews.obterDataValida();
        tarefaComPrazo.setPrazo(ManipulaData.retornaLocalDate(dataString));

    }


    public List<TarefaDTO> retornaTarefasDTODeUmProjetoPeloTipo(int tipoTarefa, Long idProjeto) {
        if (tipoTarefa == 0) {
            return tarefaSimplesService.buscaTarefasDTODeUmProjeto(idProjeto);
        } else {
            return tarefaComPrazoService.buscaTarefasDTODeUmProjeto(idProjeto);
        }
    }


    public Long selecionarProjetoComVerificacao() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            projetosViews.exibirAlertaSemProjeto();
            return null;
        }

        return SelecionaDTO.selecionaProjetoDTO(projetos);
    }


}
