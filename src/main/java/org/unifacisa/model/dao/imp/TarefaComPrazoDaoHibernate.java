package org.unifacisa.model.dao.imp;

import org.unifacisa.exceptions.GlobalExceptionHandler;
import org.unifacisa.model.DTOs.TarefaDTO;
import org.unifacisa.model.dao.TarefaComPrazoDao;
import org.unifacisa.model.entities.TarefaComPrazo;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

public class TarefaComPrazoDaoHibernate implements TarefaComPrazoDao {

    private final EntityManagerFactory entityManagerFactory;

    public TarefaComPrazoDaoHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public void criaTarefa(TarefaComPrazo tarefa) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(tarefa);
            transaction.commit();

        } catch (PersistenceException error) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handlePersistenceException(error);

        } catch (Exception error) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handleGeneralException(error);

        } finally {
            entityManager.close();
        }
    }


    @Override
    public TarefaComPrazo getTarefaById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT tarefa FROM TarefaComPrazo tarefa WHERE tarefa.id =: id", TarefaComPrazo.class).setParameter("id", id).getSingleResult();

        } catch (NoResultException error) {
            GlobalExceptionHandler.handleNoResultException(error);
            return null;
        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return null;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<TarefaDTO> getTarefasDTO() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery(
                    "SELECT new org.unifacisa.model.DTOs.TarefaDTO(tarefa.id, tarefa.titulo) FROM TarefaComPrazo tarefa",
                    TarefaDTO.class).getResultList();

        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<TarefaDTO> getTarefasDTOByPrioridade(int prioridade) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery(
                    "SELECT new org.unifacisa.model.DTOs.TarefaDTO(tarefa.id, tarefa.titulo) FROM TarefaComPrazo tarefa WHERE tarefa.prioridade =: prioridade",
                    TarefaDTO.class).setParameter("prioridade", prioridade).getResultList();

        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<TarefaDTO> getTarefasDTOByStatus(boolean emAberto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery(
                    "SELECT new org.unifacisa.model.DTOs.TarefaDTO(tarefa.id, tarefa.titulo) FROM TarefaComPrazo tarefa WHERE tarefa.emAberto =: emAberto",
                    TarefaDTO.class).setParameter("emAberto", emAberto).getResultList();

        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void atualizaTarefa(TarefaComPrazo tarefaModifica) {
        if (tarefaModifica == null || tarefaModifica.getId() == null) {
            GlobalExceptionHandler.handleIllegalArgumentException("Tarefa ou ID inválido.");
            return;
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            TarefaComPrazo tarefaExistente = entityManager.find(TarefaComPrazo.class, tarefaModifica.getId());

            if (tarefaExistente != null) {

                tarefaExistente.setTitulo(tarefaModifica.getTitulo());
                tarefaExistente.setDescricao(tarefaModifica.getDescricao());
                tarefaExistente.setPrioridade(tarefaModifica.getPrioridade());
                tarefaExistente.setEmAberto(tarefaModifica.isEmAberto());
                tarefaExistente.setPrazo(tarefaModifica.getPrazo());

                transaction.commit();
            } else {
                GlobalExceptionHandler.handleRuntimeException("Tarefa nao encontrado para atualizacao.");
            }

        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handlePersistenceException(e);

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handleGeneralException(e);

        } finally {
            entityManager.close();
        }
    }


    @Override
    public void deletaTarefaById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            TarefaComPrazo tarefaComPrazo = entityManager.find(TarefaComPrazo.class, id);

            if (tarefaComPrazo != null) {
                entityManager.remove(tarefaComPrazo);
                transaction.commit();
            }

        } catch (Exception error) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handleGeneralException(error);
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void executaTarefaById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            TarefaComPrazo tarefaComPrazo = entityManager.createQuery("SELECT tarefa FROM TarefaComPrazo tarefa WHERE tarefa.id =: id", TarefaComPrazo.class).setParameter("id", id).getSingleResult();

            tarefaComPrazo.setEmAberto(false);
            transaction.commit();

        } catch (NoResultException error) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handleNoResultException(error);
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handlePersistenceException(e);

        } catch (Exception error) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handleGeneralException(error);
        } finally {
            entityManager.close();
        }
    }
}
