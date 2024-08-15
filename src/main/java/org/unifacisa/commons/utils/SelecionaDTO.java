package org.unifacisa.commons.utils;

import org.unifacisa.exceptions.GlobalExceptionHandler;
import org.unifacisa.model.DTOs.ProjetoDTO;

import javax.swing.*;
import java.util.List;

public class SelecionaDTO {

    private SelecionaDTO() {
    }

    public static Long selecionaProjetoDTO(List<ProjetoDTO> projetos) {
        Object[] opcoes = converterProjetosParaArray(projetos);
        String projetoSelecionado = exibirDTOsView(opcoes);


        ProjetoDTO projeto = buscarProjetoPorTitulo(projetos, projetoSelecionado);

        if (projeto != null) {
            return projeto.getId();
        } else {
            GlobalExceptionHandler.handleNoResultException("Projeto não encontrado.");
            return null;
        }
    }

    private static Object[] converterProjetosParaArray(List<ProjetoDTO> projetos) {
        return projetos.stream()
                .map(ProjetoDTO::toString)
                .toArray(Object[]::new);
    }

    private static String exibirDTOsView(Object[] options) {
        Object opcaoSelecionada = JOptionPane.showInputDialog(
                null,
                "Escolha uma opção",
                "Menu",
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (opcaoSelecionada != null) {
            return opcaoSelecionada.toString();
        } else {
            GlobalExceptionHandler.handleGeneralException("Nenhuma opção foi selecionada.");
            return null;
        }
    }


    public static ProjetoDTO buscarProjetoPorTitulo(List<ProjetoDTO> projetos, String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return null;
        }

        for (ProjetoDTO projeto : projetos) {
            if (projeto != null && projeto.getTitulo() != null && projeto.getTitulo().equalsIgnoreCase(titulo.trim())) {
                return projeto;
            }
        }
        return null;
    }


}

