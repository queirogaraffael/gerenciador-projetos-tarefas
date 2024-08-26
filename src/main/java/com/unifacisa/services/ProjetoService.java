package com.unifacisa.services;

import com.unifacisa.dtos.ProjetoDTO;
import com.unifacisa.model.dao.ProjetoDao;
import com.unifacisa.model.dao.imp.ProjetoDaoHibernate;
import com.unifacisa.model.domain.entities.Projeto;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjetoService {

    private final ProjetoDao projetoDao;
    private final Map<String, List<ProjetoDTO>> projetosDTOCache;


    public ProjetoService(EntityManagerFactory entityManagerFactory) {
        this.projetoDao = new ProjetoDaoHibernate(entityManagerFactory);
        this.projetosDTOCache = new HashMap<>();
    }


    public void criarProjeto(Projeto projeto) {
        projetoDao.criaProjeto(projeto);
        projetosDTOCache.clear();
    }


    public Projeto getProjetoById(Long idProjeto) {
        return projetoDao.getProjetoById(idProjeto);
    }


    public List<ProjetoDTO> buscaProjetosDTO() {
        return projetosDTOCache.computeIfAbsent("all", key -> projetoDao.getProjetosDTO());
    }


    public List<ProjetoDTO> buscaProjetosDTOPorNome(String titulo) {
        return projetoDao.getProjetosDTOPorNome(titulo);
    }


    public void atualizarProjeto(Projeto projetoModificado) {
        projetoDao.atualizaProjetoById(projetoModificado);
        projetosDTOCache.clear();
    }


    public void deletarProjeto(Long idProjeto) {
        projetoDao.deletaProjetoById(idProjeto);
        projetosDTOCache.clear();
    }


    public boolean haProjetoComMesmoTitulo(String titulo) {
        return projetoDao.haProjetoComMesmoTitulo(titulo);
    }


}

