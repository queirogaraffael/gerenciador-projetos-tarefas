package com.unifacisa.model.dao.imp;

import com.unifacisa.dtos.TarefaDTO;
import com.unifacisa.enums.Prioridade;
import com.unifacisa.exceptions.GlobalExceptionHandler;
import com.unifacisa.model.dao.TarefaComPrazoDao;
import com.unifacisa.model.domain.entities.TarefaComPrazo;

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
    public TarefaComPrazo getTarefaById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT t " +
                    "FROM TarefaComPrazo t " +
                    "WHERE t.id =: id";

            return entityManager.createQuery(jpql, TarefaComPrazo.class).setParameter("id", id).getSingleResult();

        } catch (NoResultException error) {
            return null;
        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean haTarefaComMesmoTitulo(String titulo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT t " +
                    "FROM TarefaComPrazo t " +
                    "WHERE LOWER(t.titulo) = LOWER(:titulo)";

            entityManager.createQuery(jpql, TarefaComPrazo.class)
                    .setParameter("titulo", titulo.trim())
                    .getSingleResult();

            return true;

        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            GlobalExceptionHandler.handleGeneralException(e);
            return false;
        } finally {
            entityManager.close();
        }

    }

    @Override
    public List<TarefaDTO> getTarefasDTODeUmProjeto(Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT new com.unifacisa.dtos.TarefaDTO(t.id, t.titulo) " +
                    "FROM TarefaComPrazo t " +
                    "WHERE t.projeto.id = :idProjeto";

            return entityManager.createQuery(
                            jpql,
                            TarefaDTO.class)
                    .setParameter("idProjeto", idProjeto)
                    .getResultList();


        } catch (Exception e) {
            GlobalExceptionHandler.handleGeneralException(e);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<TarefaDTO> getTarefasDTODeUmProjetoByPrioridade(Prioridade prioridade, Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT new com.unifacisa.dtos.TarefaDTO(t.id, t.titulo) " +
                    "FROM TarefaComPrazo t " +
                    "WHERE t.prioridade = :prioridade AND t.projeto.id = :idProjeto";

            return entityManager.createQuery(
                            jpql,
                            TarefaDTO.class)
                    .setParameter("prioridade", prioridade)
                    .setParameter("idProjeto", idProjeto)
                    .getResultList();

        } catch (Exception e) {
            GlobalExceptionHandler.handleGeneralException(e);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<TarefaDTO> getTarefasDTOByStatus(boolean emAberto, Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {

            String jpql = "SELECT new com.unifacisa.dtos.TarefaDTO(t.id, t.titulo) " +
                    "FROM TarefaComPrazo t " +
                    "WHERE t.emAberto = :emAberto AND t.projeto.id = :idProjeto";

            return entityManager.createQuery(
                            jpql,
                            TarefaDTO.class)
                    .setParameter("emAberto", emAberto)
                    .setParameter("idProjeto", idProjeto)
                    .getResultList();

        } catch (Exception e) {
            GlobalExceptionHandler.handleGeneralException(e);
            return Collections.emptyList();

        } finally {
            entityManager.close();
        }
    }


    @Override
    public void atualizaTarefa(TarefaComPrazo tarefaModifica) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            String jpql = "SELECT t " +
                    "FROM TarefaComPrazo t " +
                    "WHERE t.id =: id";

            TarefaComPrazo tarefaExistente = entityManager.createQuery(jpql, TarefaComPrazo.class).setParameter("id", tarefaModifica.getId()).getSingleResult();

            tarefaExistente.setTitulo(tarefaModifica.getTitulo());
            tarefaExistente.setDescricao(tarefaModifica.getDescricao());
            tarefaExistente.setPrioridade(tarefaModifica.getPrioridade());
            tarefaExistente.setEmAberto(tarefaModifica.isEmAberto());
            tarefaExistente.setPrazo(tarefaModifica.getPrazo());

            entityManager.merge(tarefaExistente);
            transaction.commit();

        } catch (NoResultException e) {
            GlobalExceptionHandler.handleNoResultException("Entidade nao encontrada para modificacao." + e.getMessage());
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

            String jpql = "SELECT t " +
                    "FROM TarefaComPrazo t " +
                    "WHERE t.id =: id";

            TarefaComPrazo tarefaComPrazo = entityManager.createQuery(jpql, TarefaComPrazo.class).setParameter("id", id).getSingleResult();


            entityManager.remove(tarefaComPrazo);
            transaction.commit();

        } catch (NoResultException e) {
            GlobalExceptionHandler.handleNoResultException("Entidade nao encontrada para delecao." + e.getMessage());
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
    public void executaTarefaById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            String jpql = "SELECT t " +
                    "FROM TarefaComPrazo t " +
                    "WHERE t.id =: id";

            TarefaComPrazo tarefa = entityManager.createQuery(jpql, TarefaComPrazo.class).setParameter("id", id).getSingleResult();

            tarefa.setEmAberto(false);

            entityManager.merge(tarefa);
            transaction.commit();

        } catch (NoResultException e) {
            GlobalExceptionHandler.handleNoResultException("Problema ao busca entidade para execucao. " + e.getMessage());
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

            String jpql = "UPDATE TarefaComPrazo t " +
                    "SET t.emAberto = false " +
                    "WHERE t.projeto.id = :idProjeto";

            entityManager.flush();
            entityManager.createQuery(jpql)
                    .setParameter("idProjeto", idProjeto)
                    .executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handleGeneralException(e);
        } finally {
            entityManager.close();
        }


    }

}
