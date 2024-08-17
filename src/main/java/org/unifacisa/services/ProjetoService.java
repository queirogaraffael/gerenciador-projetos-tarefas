package org.unifacisa.services;

import org.unifacisa.DTOs.ProjetoDTO;
import org.unifacisa.model.dao.ProjetoDao;
import org.unifacisa.model.dao.imp.ProjetoDaoHibernate;
import org.unifacisa.model.domain.entities.Projeto;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ProjetoService {
    private final ProjetoDao projetoDao;

    public ProjetoService(EntityManagerFactory entityManagerFactory) {
        this.projetoDao = new ProjetoDaoHibernate(entityManagerFactory);
    }

    public void criarProjeto(Projeto projeto) {
        projetoDao.criaProjeto(projeto);
    }

    public Projeto getProjetoById(Long idProjeto) {
        return projetoDao.getProjetoById(idProjeto);
    }

    public List<ProjetoDTO> buscaProjetosDTO() {
        return projetoDao.getProjetosDTO();
    }

    public List<ProjetoDTO> buscaProjetosDTOPorNome(String titulo) {
        return projetoDao.getProjetosDTOPorNome(titulo);
    }

    public void atualizarProjeto(Projeto projetoModificado) {
        projetoDao.atualizaProjetoById(projetoModificado);
    }

    public void deletarProjeto(Long idProjeto) {
        projetoDao.deletaProjetoById(idProjeto);
    }

    public boolean verificaSeHaProjetoComMesmoTitulo(String titulo) {
        return projetoDao.verificaSeHaProjetoComMesmoTitulo(titulo);
    }


}

