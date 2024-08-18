package com.unifacisa.services;

import com.unifacisa.commons.utils.ManipulaData;
import com.unifacisa.dtos.TarefaDTO;
import com.unifacisa.model.dao.TarefaComPrazoDao;
import com.unifacisa.model.dao.imp.TarefaComPrazoDaoHibernate;
import com.unifacisa.enums.Prioridade;
import com.unifacisa.model.domain.entities.Projeto;
import com.unifacisa.model.domain.entities.TarefaComPrazo;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TarefaComPrazoService {

    private final TarefaComPrazoDao tarefaComPrazoDao;

    public TarefaComPrazoService(EntityManagerFactory entityManagerFactory) {
        this.tarefaComPrazoDao = new TarefaComPrazoDaoHibernate(entityManagerFactory);
    }

    public TarefaComPrazo getTarefaById(Long id) {
        return tarefaComPrazoDao.getTarefaById(id);
    }

    public List<TarefaDTO> buscaTarefasDTODeUmProjeto(Long idProjeto) {
        return tarefaComPrazoDao.getTarefasDTODeUmProjeto(idProjeto);
    }

    public List<TarefaDTO> getTarefasDTODeUmProjetoByPrioridade(Prioridade prioridade, Long idProjeto) {
        return tarefaComPrazoDao.getTarefasDTODeUmProjetoByPrioridade(prioridade, idProjeto);
    }

    public List<TarefaDTO> buscaTarefasDTOByStatus(boolean emAberto, Long idProjeto) {
        return tarefaComPrazoDao.getTarefasDTOByStatus(emAberto, idProjeto);
    }

    public void atualizarTarefa(TarefaComPrazo tarefaModificada) {
        tarefaComPrazoDao.atualizaTarefa(tarefaModificada);
    }

    public void deletarTarefa(Long id) {
        tarefaComPrazoDao.deletaTarefaById(id);
    }

    public void executaTarefa(Long id) {
        tarefaComPrazoDao.executaTarefaById(id);
    }

    public boolean verificaSeHaTarefaComMesmoTitulo(String titulo) {
        return tarefaComPrazoDao.verificaSeHaTarefaComMesmoTitulo(titulo);
    }

    public void executaTarefasPorProjeto(Long idProjeto){
        tarefaComPrazoDao.executaTarefasPorProjeto(idProjeto);
    }


    public void criaTarefaComPrazoESalva(String titulo, String descricao, Projeto projeto, Prioridade prioridade, String dataString) {
        TarefaComPrazo tarefaComPrazo = new TarefaComPrazo();
        tarefaComPrazo.setTitulo(titulo);
        tarefaComPrazo.setDescricao(descricao);
        tarefaComPrazo.setEmAberto(true);
        tarefaComPrazo.setPrioridade(prioridade);
        tarefaComPrazo.setPrazo(ManipulaData.retornaLocalDate(dataString));

        tarefaComPrazo.setProjeto(projeto);

        tarefaComPrazoDao.criaTarefa(tarefaComPrazo);

    }

}

