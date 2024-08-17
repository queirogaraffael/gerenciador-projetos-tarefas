package org.unifacisa.model.dao;

import org.unifacisa.DTOs.ProjetoDTO;
import org.unifacisa.model.domain.entities.Projeto;

import java.util.List;

public interface ProjetoDao {
    void criaProjeto(Projeto projeto);

    Projeto getProjetoById(Long idProjeto);

    boolean verificaSeHaProjetoComMesmoTitulo(String nome);

    List<ProjetoDTO> getProjetosDTO();

    List<ProjetoDTO> getProjetosDTOPorNome(String titulo);

    void atualizaProjetoById(Projeto projeto);

    void deletaProjetoById(Long id);
}
