package org.unifacisa.view;

import org.unifacisa.commons.utils.ManipulaData;
import org.unifacisa.model.domain.entities.Prioridade;
import org.unifacisa.model.domain.entities.Tarefa;
import org.unifacisa.model.domain.entities.TarefaSimples;

import javax.swing.*;

public class TarefasControllerView {

    private TarefasControllerView() {
    }

    private static final String MENU_TITLE = "Gerenciador de Tarefas";
    private static final String MENU_PROMPT = "Escolha uma opcao: ";

    private static final Object[] MENU_OPTIONS = {

            // se marcar o projeto como concluido marca todas as suas tarefas como concluida

            "Criar Tarefa em um Projeto",
            "Atualizar Tarefa de um Projeto",
            "Visualizar Tarefa(s) de um Projeto",
            "Visualizar Tarefa(s) de um Projeto por Prioridade", // enum
            "Visualizar Tarefa(s) de um Projeto por Status", //
            "Remover Tarefa de um Projeto",
            "Executar Tarefa de um Projeto",
            "Voltar"
    };


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
        Object[] opcoes = {"Baixa", "Media", "Alta"};
        int opcaoPrioridade = JOptionPane.showOptionDialog(
                null,
                "Escolha uma prioridade para a tarefa:",
                "Alerta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
        return Prioridade.fromCodigo(opcaoPrioridade);
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
            return new Object[]{"Titulo", "Descricao", "Status", "Prioridade", "Todos", "Voltar"};
        } else {
            return new Object[]{"Titulo", "Descricao", "Status", "Prioridade", "Data", "Todos", "Voltar"};
        }
    }

}
