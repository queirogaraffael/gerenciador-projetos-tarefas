package org.unifacisa.services;

import org.unifacisa.DTOs.TarefaDTO;
import org.unifacisa.model.dao.TarefaSimplesDao;
import org.unifacisa.model.dao.imp.TarefaSimplesDaoHibernate;
import org.unifacisa.model.domain.entities.Prioridade;
import org.unifacisa.model.domain.entities.Projeto;
import org.unifacisa.model.domain.entities.TarefaSimples;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TarefaSimplesService {

    private final TarefaSimplesDao tarefaSimplesDao;

    public TarefaSimplesService(EntityManagerFactory entityManagerFactory) {
        this.tarefaSimplesDao = new TarefaSimplesDaoHibernate(entityManagerFactory);
    }

    public void criarTarefa(TarefaSimples tarefa) {
        tarefaSimplesDao.criaTarefa(tarefa);
    }

    public TarefaSimples getTarefaById(Long id) {
        return tarefaSimplesDao.getTarefaById(id);
    }

    public List<TarefaDTO> buscaTarefasDTODeUmProjeto(Long idProjeto) {
        return tarefaSimplesDao.getTarefasDTODeUmProjeto(idProjeto);
    }

    public List<TarefaDTO> buscaTarefasDTOByPrioridade(int prioridade) {
        return tarefaSimplesDao.getTarefasDTOByPrioridade(prioridade);
    }

    public List<TarefaDTO> buscaTarefasDTOByStatus(boolean emAberto, Long idProjeto) {
        return tarefaSimplesDao.getTarefasDTOByStatus(emAberto, idProjeto);
    }

    public void atualizarTarefa(TarefaSimples tarefaModificada) {
        tarefaSimplesDao.atualizaTarefa(tarefaModificada);
    }

    public void deletarTarefa(Long id) {
        tarefaSimplesDao.deletaTarefaById(id);
    }

    public void executaTarefa(Long id) {
        tarefaSimplesDao.executaTarefaById(id);
    }

    public boolean verificaSeHaTarefaComMesmoTitulo(String titulo){
        return tarefaSimplesDao.verificaSeHaTarefaComMesmoTitulo(titulo);
    }



    public void criaTarefaSimplesESalva(String titulo, String descricao, Projeto projeto, Prioridade prioridade) {
        TarefaSimples tarefaSimples = new TarefaSimples();
        tarefaSimples.setTitulo(titulo);
        tarefaSimples.setDescricao(descricao);
        tarefaSimples.setEmAberto(true);
        tarefaSimples.setPrioridade(prioridade);
        tarefaSimples.setProjeto(projeto);

        tarefaSimplesDao.criaTarefa(tarefaSimples);
    }
}

