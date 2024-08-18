package com.unifacisa.view;

import com.unifacisa.commons.utils.ManipulaData;
import com.unifacisa.exceptions.GlobalExceptionHandler;
import com.unifacisa.enums.Prioridade;
import com.unifacisa.model.domain.entities.Tarefa;
import com.unifacisa.model.domain.entities.TarefaSimples;

import javax.swing.*;

public class TarefasViews {

    private TarefasViews() {
    }

    private static final String MENU_TITLE = "Gerenciador de Tarefas";
    private static final String MENU_PROMPT = "Escolha uma opcao: ";

    private static final Object[] MENU_OPTIONS = {
            "Criar Tarefa em um Projeto",
            "Atualizar Tarefa de um Projeto",
            "Visualizar Tarefa(s) de um Projeto",
            "Visualizar Tarefa(s) de um Projeto por Prioridade",
            "Visualizar Tarefa(s) de um Projeto por Status",
            "Remover Tarefa de um Projeto",
            "Executar Tarefa de um Projeto",
            "Voltar"
    };

    private static final Object[] PRIORIDADE_OPTIONS = {"Baixa", "Media", "Alta"};
    private static final Object[] STATUS_OPTIONS = {"Em aberto", "Concluida"};
    private static final Object[] MODIFICACAO_OPCOES_SIMPLIFICADA = {"Titulo", "Descricao", "Status", "Prioridade", "Todos", "Voltar"};
    private static final Object[] MODIFICACAO_OPCOES_COMPLETA = {"Titulo", "Descricao", "Status", "Prioridade", "Data", "Todos", "Voltar"};


    public static String exibirMenuTarefasView() {
        Object opcaoSelecionada = JOptionPane.showInputDialog(
                null,
                MENU_PROMPT,
                MENU_TITLE,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                MENU_OPTIONS,
                MENU_OPTIONS[0]
        );

        return opcaoSelecionada.toString();
    }

    public static Prioridade selecionarPrioridade() {
        int opcaoPrioridade = JOptionPane.showOptionDialog(
                null,
                "Escolha uma prioridade para a tarefa:",
                "Alerta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                PRIORIDADE_OPTIONS,
                PRIORIDADE_OPTIONS[0]
        );
        return Prioridade.fromCodigo(opcaoPrioridade);
    }


    public static String exibirTarefasDTOsView(Object[] options) {
        Object opcaoSelecionada = JOptionPane.showInputDialog(
                null,
                "Escolha uma tarefa: ",
                "Tarefas",
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (opcaoSelecionada != null) {
            return opcaoSelecionada.toString();
        } else {
            GlobalExceptionHandler.handleGeneralException("Nenhuma opcao foi selecionada.");
            return null;
        }
    }

    public static int exibeViewEscolhaTipoTarefa() {
        Object[] opcoes = {"Tarefa simples", "Tarefa com prazo"};
        return JOptionPane.showOptionDialog(
                null,
                "Escolha o tipo de tarefa:",
                "Alerta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    }


    public static boolean exibeViewEscolhaStatusTarefa() {
        int resultado = JOptionPane.showOptionDialog(
                null,
                "Escolha o status da tarefa:",
                "Alerta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                STATUS_OPTIONS,
                STATUS_OPTIONS[0]
        );
        return resultado == 0;
    }


    public static int exibirEscolhaModificacao(Object[] opcoes) {
        return JOptionPane.showOptionDialog(
                null,
                "Escolha uma opção:",
                "Modificar Tarefa",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    }



    public static boolean desejaAdicionarData() {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja adicionar data à tarefa?", "Alerta", JOptionPane.YES_NO_OPTION);
        return resposta == JOptionPane.YES_OPTION;
    }


    public static String obterDataValida() {
        String dataString = JOptionPane.showInputDialog("Digite uma data no formato " + ManipulaData.FORMATO_DATA);
        while (!ManipulaData.verificaFormatoDataEstaCorreto(dataString)) {
            dataString = JOptionPane.showInputDialog("Digite uma data válida no formato " + ManipulaData.FORMATO_DATA);
        }
        return dataString;
    }


    public static Object[] obterOpcoesDeModificacao(Tarefa tarefa) {
        if (tarefa instanceof TarefaSimples) {
            return MODIFICACAO_OPCOES_SIMPLIFICADA;
        } else {
            return MODIFICACAO_OPCOES_COMPLETA;
        }
    }


    public static <T extends Tarefa> void atualizaPrioridade(T tarefa) {
        Prioridade prioridade = selecionarPrioridade();
        tarefa.setPrioridade(prioridade);
    }


    public static <T extends Tarefa> void atualizarStatus(T tarefa) {
        int opcaoStatus = JOptionPane.showOptionDialog(null, "Escolha o status:", "Modificar Status",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, STATUS_OPTIONS, STATUS_OPTIONS[0]);

        if (opcaoStatus == 0) {
            tarefa.setEmAberto(true);
        } else if (opcaoStatus == 1) {
            tarefa.setEmAberto(false);
        }
    }

    public static <T extends Tarefa> void atualizarDescricao(T tarefa) {
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

    public static <T extends Tarefa> void atualizarTitulo(T tarefa) {
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

    public static void exibirAlertaSemTarefasSimples() {
        JOptionPane.showMessageDialog(null, "Sem tarefas simples.");

    }

    public static void exibirAlertaTarefaCriadaComSucesso() {
        JOptionPane.showMessageDialog(null, "Tarefa criada com sucesso!");

    }

    public static void exibirAlertaTarefaModificadaComSucesso() {
        JOptionPane.showMessageDialog(null, "Tarefa modificada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void exibirAlertaSemTarefasComPrazo() {
        JOptionPane.showMessageDialog(null, "Sem tarefas com prazo.");

    }

    public static void exibirAlertaTarefaExecutadaComSucesso() {
        JOptionPane.showMessageDialog(null, "Tarefa executada com sucesso!");

    }

    public static void exibirAlertaSemTarefasDoTipoNoProjeto() {
        JOptionPane.showMessageDialog(null, "Sem tarefas desse tipo no projeto.");

    }

    public static void exibirAlertaTarefaDeletadaComSucesso() {
        JOptionPane.showMessageDialog(null, "Tarefa deletada com sucesso!");

    }

    public static void exibirAlertaQueNaoPodeTarefaComNomeDuplicadoEmUmProjeto() {
        JOptionPane.showMessageDialog(null, "Nao pode ter mais de uma tarefa com o mesmo titulo em um projeto");

    }

    public static void exibirAlertaTituloTarefaNaoPodeSerVazio() {
        JOptionPane.showMessageDialog(null, "O título não pode ser vazio.", "Alerta", JOptionPane.ERROR_MESSAGE);

    }

    public static void exibirAlertaDescricaoTarefaNaoPoderSerVazia() {
        JOptionPane.showMessageDialog(null, "A descricao nao pode ser vazia.", "Alerta", JOptionPane.ERROR_MESSAGE);

    }


    public static String leTituloTarefa() {
        return JOptionPane.showInputDialog("Digite o titulo: ");
    }

    public static String leDescricaoTarefa() {
        return JOptionPane.showInputDialog("Digite a descricao: ");
    }

    public static void exibeTarefa(Tarefa tarefa) {
        JOptionPane.showMessageDialog(null, tarefa);

    }



}
