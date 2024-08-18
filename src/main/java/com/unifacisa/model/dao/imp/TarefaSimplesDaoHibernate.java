package com.unifacisa.model.dao.imp;

import com.unifacisa.dtos.TarefaDTO;
import com.unifacisa.exceptions.GlobalExceptionHandler;
import com.unifacisa.model.dao.TarefaSimplesDao;
import com.unifacisa.enums.Prioridade;
import com.unifacisa.model.domain.entities.TarefaSimples;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

public class TarefaSimplesDaoHibernate implements TarefaSimplesDao {

    private final EntityManagerFactory entityManagerFactory;

    public TarefaSimplesDaoHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void criaTarefa(TarefaSimples tarefa) {
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
    public TarefaSimples getTarefaById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT tarefa FROM TarefaSimples tarefa WHERE tarefa.id =: id", TarefaSimples.class).setParameter("id", id).getSingleResult();

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
    public boolean verificaSeHaTarefaComMesmoTitulo(String titulo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.createQuery("SELECT tarefa FROM TarefaSimples tarefa WHERE LOWER(tarefa.titulo) = LOWER(:titulo)", TarefaSimples.class)
                    .setParameter("titulo", titulo.trim())
                    .getSingleResult();

            return true;

        } catch (Exception error) {
            return false;
        } finally {
            entityManager.close();
        }


    }

    @Override
    public List<TarefaDTO> getTarefasDTODeUmProjeto(Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery(
                            "SELECT new com.unifacisa.dtos.TarefaDTO(tarefa.id, tarefa.titulo) " +
                                    "FROM TarefaSimples tarefa " +
                                    "WHERE tarefa.projeto.id = :idProjeto",
                            TarefaDTO.class)
                    .setParameter("idProjeto", idProjeto)
                    .getResultList();


        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<TarefaDTO> getTarefasDTODeUmProjetoByPrioridade(Prioridade prioridade, Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery(
                            "SELECT new com.unifacisa.dtos.TarefaDTO(tarefa.id, tarefa.titulo) " +
                                    "FROM TarefaSimples tarefa " +
                                    "WHERE tarefa.prioridade = :prioridade AND tarefa.projeto.id = :idProjeto",
                            TarefaDTO.class)
                    .setParameter("prioridade", prioridade)
                    .setParameter("idProjeto", idProjeto)
                    .getResultList();
        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }



    @Override
    public List<TarefaDTO> getTarefasDTOByStatus(boolean emAberto, Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery(
                            "SELECT new com.unifacisa.dtos.TarefaDTO(tarefa.id, tarefa.titulo) " +
                                    "FROM TarefaSimples tarefa " +
                                    "WHERE tarefa.emAberto = :emAberto AND tarefa.projeto.id = :idProjeto",
                            TarefaDTO.class)
                    .setParameter("emAberto", emAberto)
                    .setParameter("idProjeto", idProjeto)
                    .getResultList();

        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void atualizaTarefa(TarefaSimples tarefaModifica) {
        if (tarefaModifica == null || tarefaModifica.getId() == null) {
            GlobalExceptionHandler.handleIllegalArgumentException("Tarefa ou ID invalido.");
            return;
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            TarefaSimples tarefaExistente = entityManager.find(TarefaSimples.class, tarefaModifica.getId());

            if (tarefaExistente != null) {

                tarefaExistente.setTitulo(tarefaModifica.getTitulo());
                tarefaExistente.setDescricao(tarefaModifica.getDescricao());
                tarefaExistente.setPrioridade(tarefaModifica.getPrioridade());
                tarefaExistente.setEmAberto(tarefaModifica.isEmAberto());

                transaction.commit();
            } else {
                GlobalExceptionHandler.handleRuntimeException("Tarefa nao encontrada para atualizacao.");
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

            TarefaSimples tarefaSimples = entityManager.find(TarefaSimples.class, id);

            if (tarefaSimples != null) {
                entityManager.remove(tarefaSimples);
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
            TarefaSimples tarefaSimples = entityManager.createQuery("SELECT tarefa FROM TarefaSimples tarefa WHERE tarefa.id =: id", TarefaSimples.class).setParameter("id", id).getSingleResult();

            tarefaSimples.setEmAberto(false);
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

    @Override
    public void executaTarefasPorProjeto(Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.createQuery(
                            "UPDATE TarefaSimples tarefa SET tarefa.emAberto = false WHERE tarefa.projeto.id = :idProjeto")
                    .setParameter("idProjeto", idProjeto)
                    .executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            GlobalExceptionHandler.handleGeneralException(e);
        } finally {
            entityManager.close();
        }


    }


}
