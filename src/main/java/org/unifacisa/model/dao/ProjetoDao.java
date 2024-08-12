package org.unifacisa.model.dao;

import org.unifacisa.model.DTOs.ProjetoDTO;
import org.unifacisa.model.entities.Projeto;

import java.util.List;

public interface ProjetoDao {
    void criaProjeto(Projeto projeto);
    Projeto getProjetoById(Long idProjeto);
    List<ProjetoDTO> getProjetosDTO();
    List<ProjetoDTO> getProjetosDTOPorNome(String titulo);
    void atualizaProjetoById(Projeto projeto);
    void deletaProjetoById(Long id);
}
