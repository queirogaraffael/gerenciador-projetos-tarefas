package com.unifacisa.view;

import com.unifacisa.model.domain.entities.Projeto;

import javax.swing.*;

public class ProjetosViews {

    private static final String MENU_TITLE = "Gerenciador de Projeto";
    private static final String MENU_PROMPT = "Escolha uma opcao: ";

    public ProjetosViews() {
    }

    private final Object[] menuOptions = {
            "Criar Projeto",
            "Atualizar Projeto",
            "Visualizar Projeto(s)",
            "Buscar e Visualizar Projeto(s) por Nome",
            "Remover Projeto",
            "Executar Projeto",
            "Voltar"
    };

    public String exibirMenuProjetosView() {
        Object opcaoSelecionada = JOptionPane.showInputDialog(
                null,
                MENU_PROMPT,
                MENU_TITLE,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                menuOptions,
                menuOptions[0]
        );

        return opcaoSelecionada.toString();
    }


    public int selecionaOpcaoDeModificacaoProjeto() {

        Object[] opcoes = {"Titulo", "Descricao", "Todos", "Voltar"};

        return JOptionPane.showOptionDialog(null, "Escolha uma opcao:", "Modificar Projeto",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

    }

    public void exibeDadosProjetos(Projeto projeto) {
        JOptionPane.showMessageDialog(null, projeto.toString());

    }


    public String leTituloProjeto() {
        return JOptionPane.showInputDialog("Digite o titulo do projeto: ");
    }

    public String leDescricaoProjeto() {
        return JOptionPane.showInputDialog("Digite uma descricao: ");
    }

    public void exibirAlertaSemProjetosCorrespondentes() {
        JOptionPane.showMessageDialog(null, "Sem projeto(s) correspondentes.", "Alerta"
                , JOptionPane.ERROR_MESSAGE);
    }

    public void exibirAlertaProjetoRemovidoComSucesso() {
        JOptionPane.showMessageDialog(null, "Projeto removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

    }

    public void exibirAlertaProjetoComMesmoTitulo() {
        JOptionPane.showMessageDialog(null, "Projeto com o mesmo titulo ja existe. Tente com um outro titulo.", "Alerta", JOptionPane.ERROR_MESSAGE);

    }


    public void exibirAlertaProjetoExecutadoComSucesso() {
        JOptionPane.showMessageDialog(null, "Projeto executada com sucesso!");

    }

    public void exibirAlertaTituloNaoPodeSerVazio() {
        JOptionPane.showMessageDialog(null, "O título não pode ser vazio.", "Alerta", JOptionPane.ERROR_MESSAGE);

    }

    public void exibirAlertaDescricaoNaoPodeSerVazia() {
        JOptionPane.showMessageDialog(null, "A descricao nao pode ser vazia.", "Alerta", JOptionPane.ERROR_MESSAGE);

    }

    public void exibirAlertaSemProjeto() {
        JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta", JOptionPane.ERROR_MESSAGE);
    }

    public void exibirAlertaProjetoModificadoComSucesso() {
        JOptionPane.showMessageDialog(null, "Projeto modificado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void exibirAlertaProjetoFoiCriadoComSucesso() {
        JOptionPane.showMessageDialog(null, "Projeto criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

    }

    public void atualizarTitulo(Projeto projeto) {
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

    public void atualizarDescricao(Projeto projeto) {
        String novaDescricao;
        while (true) {
            novaDescricao = JOptionPane.showInputDialog("Digite a nova descricao: ");
            if (novaDescricao != null && !novaDescricao.trim().isEmpty()) {
                projeto.setDescricao(novaDescricao);
                return;
            }
            JOptionPane.showMessageDialog(null, "A descricao nao pode ser vazia. Digite uma nova descricao!", "Alerta", JOptionPane.ERROR_MESSAGE);
        }
    }


}
