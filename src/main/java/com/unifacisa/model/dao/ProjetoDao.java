package com.unifacisa.model.dao;

import com.unifacisa.model.domain.entities.Projeto;
import com.unifacisa.dtos.ProjetoDTO;

import java.util.List;

public interface ProjetoDao {
    void criaProjeto(Projeto projeto);

    Projeto getProjetoById(Long idProjeto);

    boolean haProjetoComMesmoTitulo(String nome);

    List<ProjetoDTO> getProjetosDTO();

    List<ProjetoDTO> getProjetosDTOPorNome(String titulo);

    void atualizaProjetoById(Projeto projeto);

    void deletaProjetoById(Long id);
}
