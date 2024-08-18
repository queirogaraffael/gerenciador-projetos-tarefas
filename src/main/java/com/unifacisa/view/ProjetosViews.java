package com.unifacisa.view;

import com.unifacisa.exceptions.GlobalExceptionHandler;
import com.unifacisa.model.domain.entities.Projeto;

import javax.swing.JOptionPane;

public class ProjetosViews {

    private ProjetosViews() {
    }

    private static final String MENU_TITLE = "Gerenciador de Projeto";
    private static final String MENU_PROMPT = "Escolha uma opcao: ";

    private static final Object[] MENU_OPTIONS = {
            "Criar Projeto",
            "Atualizar Projeto",
            "Visualizar Projeto(s)",
            "Buscar e Visualizar Projeto(s) por Nome",
            "Remover Projeto",
            "Executar Projeto",
            "Voltar"
    };

    public static String exibirMenuProjetosView() {
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


    public static String exibirProjetosDTOsView(Object[] options) {
        Object opcaoSelecionada = JOptionPane.showInputDialog(
                null,
                "Escolha um projeto: ",
                "Projetos",
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (opcaoSelecionada != null) {
            return opcaoSelecionada.toString();
        } else {
            GlobalExceptionHandler.handleGeneralException("Nenhum projeto foi selecionado.");
            return null;
        }
    }


    public static int selecionaOpcaoDeModificacaoProjeto() {

        Object[] opcoes = {"Titulo", "Descricao", "Todos", "Voltar"};

        return JOptionPane.showOptionDialog(null, "Escolha uma opcao:", "Modificar Projeto",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

    }

    public static void exibeDadosProjetos(Projeto projeto){
        JOptionPane.showMessageDialog(null, projeto.toString());

    }


    public static String leTituloProjeto(){
        return JOptionPane.showInputDialog("Digite o titulo do projeto: ");
    }

    public static String leDescricaoProjeto(){
        return JOptionPane.showInputDialog("Digite uma descricao: ");
    }

    public static void exibirAlertaSemProjetosCorrespondentes(){
        JOptionPane.showMessageDialog(null, "Sem projeto(s) correspondentes.", "Alerta"
                , JOptionPane.ERROR_MESSAGE);
    }

    public static void exibirAlertaProjetoRemovidoComSucesso(){
        JOptionPane.showMessageDialog(null, "Projeto removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void exibirAlertaProjetoComMesmoTitulo(){
        JOptionPane.showMessageDialog(null, "Projeto com o mesmo titulo ja existe. Tente com um outro titulo.", "Alerta", JOptionPane.ERROR_MESSAGE);

    }



    public static void exibirAlertaProjetoExecutadoComSucesso(){
        JOptionPane.showMessageDialog(null, "Projeto executada com sucesso!");

    }

    public static void exibirAlertaTituloNaoPodeSerVazio(){
        JOptionPane.showMessageDialog(null, "O título não pode ser vazio.", "Alerta", JOptionPane.ERROR_MESSAGE);

    }

    public static void exibirAlertaDescricaoNaoPodeSerVazia(){
        JOptionPane.showMessageDialog(null, "A descricao nao pode ser vazia.", "Alerta", JOptionPane.ERROR_MESSAGE);

    }

    public static void exibirAlertaSemProjeto() {
        JOptionPane.showMessageDialog(null, "Sem projeto(s). Adicione primeiro.", "Alerta", JOptionPane.ERROR_MESSAGE);
    }

    public static void exibirAlertaProjetoModificadoComSucesso() {
        JOptionPane.showMessageDialog(null, "Projeto modificado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void exibirAlertaProjetoFoiCriadoComSucesso(){
        JOptionPane.showMessageDialog(null, "Projeto criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void atualizarTitulo(Projeto projeto) {
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

    public static void atualizarDescricao(Projeto projeto) {
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
