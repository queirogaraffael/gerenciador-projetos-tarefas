package org.unifacisa.controllers;

import org.unifacisa.DTOs.ProjetoDTO;
import org.unifacisa.DTOs.TarefaDTO;
import org.unifacisa.commons.constantes.ConstantesMenuTarefasController;
import org.unifacisa.commons.utils.ManipulaData;
import org.unifacisa.commons.utils.SelecionaIdDTO;
import org.unifacisa.model.domain.entities.*;
import org.unifacisa.services.ProjetoService;
import org.unifacisa.services.TarefaComPrazoService;
import org.unifacisa.services.TarefaSimplesService;
import org.unifacisa.view.TarefasControllerView;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.util.List;

public class MenuTarefasController {

    private final ProjetoService projetoService;
    private final TarefaComPrazoService tarefaComPrazoService;
    private final TarefaSimplesService tarefaSimplesService;

    public MenuTarefasController(EntityManagerFactory entityManagerFactory) {
        this.projetoService = new ProjetoService(entityManagerFactory);
        this.tarefaComPrazoService = new TarefaComPrazoService(entityManagerFactory);
        this.tarefaSimplesService = new TarefaSimplesService(entityManagerFactory);
    }

    public void menuGerenciadorTarefas() {
        String opcaoMenuGerenciadoTarefas;

        do {
            opcaoMenuGerenciadoTarefas = TarefasControllerView.exibirMenuTarefasView();

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

                    break;

                case (ConstantesMenuTarefasController.VISUALIZAR_TAREFA_POR_STATUS):
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

        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();


        if (projetos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta", JOptionPane.ERROR_MESSAGE);

            return;
        }


        Long id = SelecionaIdDTO.selecionaProjetoDTO(projetos);
        Projeto projeto = projetoService.getProjetoById(id);


        String titulo = JOptionPane.showInputDialog("Digite o titulo: ");


        if (tarefaComPrazoService.verificaSeHaTarefaComMesmoTitulo(titulo) || tarefaSimplesService.verificaSeHaTarefaComMesmoTitulo(titulo)) {
            JOptionPane.showMessageDialog(null, "Nao pode ter mais de uma tarefa com o mesmo titulo em um projeto");
            return;
        }


        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O título não pode ser vazio.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String descricao = JOptionPane.showInputDialog("Digite a descricao: ");

        if (descricao == null || descricao.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "A descricao nao pode ser vazia.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }


        Prioridade prioridade = TarefasControllerView.selecionarPrioridade();

        if (TarefasControllerView.desejaAdicionarData()) {
            String dataString = TarefasControllerView.obterDataValida();
            tarefaComPrazoService.criaTarefaComPrazoESalva(titulo, descricao, projeto, prioridade, dataString);
        } else {
            tarefaSimplesService.criaTarefaSimplesESalva(titulo, descricao, projeto, prioridade);
        }

        JOptionPane.showMessageDialog(null, "Tarefa criada com sucesso!");
    }


    private void atualizaTarefa() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long idProjeto = SelecionaIdDTO.selecionaProjetoDTO(projetos);
        int tipoTarefa = TarefasControllerView.exibeViewEscolhaTipoTarefa();

        List<TarefaDTO> tarefas = retornaTarefasDTODeUmProjetoPeloTipo(tipoTarefa, idProjeto);

        if (tarefas == null) {
            JOptionPane.showMessageDialog(null, "Sem tarefas desse tipo no projeto.");
            return;
        }

        Long idTarefa = SelecionaIdDTO.selecionaTarefaDTO(tarefas);

        if (tipoTarefa == 0) {
            TarefaSimples tarefaSimples = tarefaSimplesService.getTarefaById(idTarefa);
            exibiOpcoesDeModificacaoTarefa(tarefaSimples);
        } else {
            TarefaComPrazo tarefaComPrazo = tarefaComPrazoService.getTarefaById(idTarefa);
            exibiOpcoesDeModificacaoTarefa(tarefaComPrazo);
        }
    }


    private void visualizarTarefa() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long idProjeto = SelecionaIdDTO.selecionaProjetoDTO(projetos);
        int tipoTarefa = TarefasControllerView.exibeViewEscolhaTipoTarefa();

        if (tipoTarefa == 0) {
            visualizarTarefaSimples(idProjeto);
        } else {
            visualizarTarefaComPrazo(idProjeto);
        }

    }


    private void deletaTarefa() {
        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long idProjeto = SelecionaIdDTO.selecionaProjetoDTO(projetos);
        int tipoTarefa = TarefasControllerView.exibeViewEscolhaTipoTarefa();

        List<TarefaDTO> tarefas = retornaTarefasDTODeUmProjetoPeloTipo(tipoTarefa, idProjeto);

        if (tarefas == null || tarefas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem tarefas desse tipo no projeto.");
            return;
        }

        Long idTarefa = SelecionaIdDTO.selecionaTarefaDTO(tarefas);

        if (tipoTarefa == 0) {
            tarefaSimplesService.deletarTarefa(idTarefa);
        } else {
            tarefaComPrazoService.deletarTarefa(idTarefa);
        }

        JOptionPane.showMessageDialog(null, "Tarefa deletada com sucesso!");
    }


    private void executaTarefa() {

        List<ProjetoDTO> projetos = projetoService.buscaProjetosDTO();

        if (projetos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long idProjeto = SelecionaIdDTO.selecionaProjetoDTO(projetos);
        int tipoTarefa = TarefasControllerView.exibeViewEscolhaTipoTarefa();


        List<TarefaDTO> tarefas = retornaTarefasDTOEmAbertoDeUmProjetoPeloTipo(tipoTarefa, idProjeto);


        if (tarefas == null || tarefas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem tarefas desse tipo no projeto.");
            return;
        }

        Long idTarefa = SelecionaIdDTO.selecionaTarefaDTO(tarefas);

        if (tipoTarefa == 0) {
            tarefaSimplesService.executaTarefa(idTarefa);
        } else {
            tarefaComPrazoService.executaTarefa(idTarefa);
        }

        JOptionPane.showMessageDialog(null, "Tarefa executada com sucesso!");

    }


    private List<TarefaDTO> retornaTarefasDTOEmAbertoDeUmProjetoPeloTipo(int tipoTarefa, Long idProjeto) {
        if (tipoTarefa == 0) {
            return tarefaSimplesService.buscaTarefasDTOByStatus(true, idProjeto);
        } else {
            return tarefaComPrazoService.buscaTarefasDTOByStatus(true, idProjeto);
        }

    }


    private void visualizarTarefaSimples(Long idProjeto) {
        List<TarefaDTO> tarefasSimples = tarefaSimplesService.buscaTarefasDTODeUmProjeto(idProjeto);

        if (tarefasSimples == null || tarefasSimples.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem tarefas simples no projeto.");
            return;
        }

        Long idTarefa = SelecionaIdDTO.selecionaTarefaDTO(tarefasSimples);
        TarefaSimples tarefa = tarefaSimplesService.getTarefaById(idTarefa);

        JOptionPane.showMessageDialog(null, tarefa);
    }

    private void visualizarTarefaComPrazo(Long idProjeto) {
        List<TarefaDTO> tarefasComPrazo = tarefaComPrazoService.buscaTarefasDTODeUmProjeto(idProjeto);

        if (tarefasComPrazo == null || tarefasComPrazo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sem tarefas com prazo no projeto.");
            return;
        }

        Long idTarefa = SelecionaIdDTO.selecionaTarefaDTO(tarefasComPrazo);
        TarefaComPrazo tarefa = tarefaComPrazoService.getTarefaById(idTarefa);
        JOptionPane.showMessageDialog(null, tarefa);
    }


    private <T extends Tarefa> void exibiOpcoesDeModificacaoTarefa(T tarefa) {
        Object[] opcoes = TarefasControllerView.obterOpcoesDeModificacao(tarefa);

        int opcao;
        do {
            opcao = JOptionPane.showOptionDialog(null, "Escolha uma opcao:", "Modificar Tarefa",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcoes, opcoes[0]);

            switch (opcao) {
                case 0:
                    atualizarTitulo(tarefa);
                    break;

                case 1:
                    atualizarDescricao(tarefa);
                    break;

                case 2:
                    atualizarStatus(tarefa);
                    break;

                case 3:
                    atualizaPrioridade(tarefa);
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

                JOptionPane.showMessageDialog(null, "Tarefa modificada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

        } while (opcao != opcoes.length - 1);
    }


    private <T extends Tarefa> void atualizarTudo(T tarefa) {
        atualizarTitulo(tarefa);
        atualizarDescricao(tarefa);
        atualizarStatus(tarefa);
        atualizaPrioridade(tarefa);
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


    private <T extends Tarefa> void atualizarTitulo(T tarefa) {
        String novoTitulo;
        while (true) {
            novoTitulo = JOptionPane.showInputDialog("Digite o novo titulo: ");
            if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
                tarefa.setTitulo(novoTitulo);
                return;
            }
            JOptionPane.showMessageDialog(null, "O titulo nao pode ser vazio. Digite um novo título!", "Alerta", JOptionPane.ERROR_MESSAGE);
        }
    }

    private <T extends Tarefa> void atualizarDescricao(T tarefa) {
        String novaDescricao;
        while (true) {
            novaDescricao = JOptionPane.showInputDialog("Digite a nova descricao: ");
            if (novaDescricao != null && !novaDescricao.trim().isEmpty()) {
                tarefa.setDescricao(novaDescricao);
                return;
            }
            JOptionPane.showMessageDialog(null, "A descricao nao pode ser vazia. Digite uma nova descricao!", "Alerta", JOptionPane.ERROR_MESSAGE);
        }
    }


    private <T extends Tarefa> void atualizarStatus(T tarefa) {
        Object[] opcoesStatus = {"Em aberto", "Concluido"};
        int opcaoStatus = JOptionPane.showOptionDialog(null, "Escolha o status:", "Modificar Status",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoesStatus, opcoesStatus[0]);

        if (opcaoStatus == 0) {
            tarefa.setEmAberto(true);
        } else if (opcaoStatus == 1) {
            tarefa.setEmAberto(false);
        }
    }

    private <T extends Tarefa> void atualizaPrioridade(T tarefa) {
        Prioridade prioridade = TarefasControllerView.selecionarPrioridade();
        tarefa.setPrioridade(prioridade);
    }

    private void atualizaData(TarefaComPrazo tarefaComPrazo) {
        String dataString = TarefasControllerView.obterDataValida();
        tarefaComPrazo.setPrazo(ManipulaData.retornaLocalDate(dataString));

    }


    public List<TarefaDTO> retornaTarefasDTODeUmProjetoPeloTipo(int tipoTarefa, Long idProjeto) {
        if (tipoTarefa == 0) {
            return tarefaSimplesService.buscaTarefasDTODeUmProjeto(idProjeto);
        } else {
            return tarefaComPrazoService.buscaTarefasDTODeUmProjeto(idProjeto);
        }
    }


}
