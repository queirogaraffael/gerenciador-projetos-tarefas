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
    public TarefaSimples getTarefaById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT t " +
                    "FROM TarefaSimples t " +
                    "WHERE t.id =: id";

            return entityManager.createQuery(jpql, TarefaSimples.class).setParameter("id", id).getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            GlobalExceptionHandler.handleGeneralException(e);
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean verificaSeHaTarefaComMesmoTitulo(String titulo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT t " +
                    "FROM TarefaSimples t " +
                    "WHERE LOWER(t.titulo) = LOWER(:titulo)";

            entityManager.createQuery(jpql, TarefaSimples.class)
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
                    "FROM TarefaSimples t " +
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
                    "FROM TarefaSimples t " +
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
                    "FROM TarefaSimples t " +
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
    public void atualizaTarefa(TarefaSimples tarefaModifica) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            String jpql = "SELECT t " +
                    "FROM TarefaSimples t " +
                    "WHERE t.id =: id";

            TarefaSimples tarefaExistente = entityManager.createQuery(jpql, TarefaSimples.class).setParameter("id", tarefaModifica.getId()).getSingleResult();

            tarefaExistente.setTitulo(tarefaModifica.getTitulo());
            tarefaExistente.setDescricao(tarefaModifica.getDescricao());
            tarefaExistente.setPrioridade(tarefaModifica.getPrioridade());
            tarefaExistente.setEmAberto(tarefaModifica.isEmAberto());

            entityManager.merge(tarefaExistente);
            transaction.commit();

        } catch (NoResultException e) {
            GlobalExceptionHandler.handleNoResultException("Tarefa nao encontrada para modificacao.");
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
                    "FROM TarefaSimples t " +
                    "WHERE t.id =: id";

            TarefaSimples tarefaSimples = entityManager.createQuery(jpql, TarefaSimples.class).setParameter("id", id).getSingleResult();

            entityManager.remove(tarefaSimples);
            transaction.commit();

        } catch (NoResultException e) {
            GlobalExceptionHandler.handleNoResultException("Tarefa nao encontrada para delecao.");
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

            String jpql = "SELECT t " +
                    "FROM TarefaSimples t " +
                    "WHERE t.id =: id";

            TarefaSimples tarefaSimples = entityManager.createQuery(jpql, TarefaSimples.class).setParameter("id", id).getSingleResult();

            tarefaSimples.setEmAberto(false);

            entityManager.merge(tarefaSimples);
            transaction.commit();

        } catch (NoResultException er) {
            GlobalExceptionHandler.handleNoResultException("Tarefa nao encontrada para execucao.");
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
    public void executaTarefasPorProjeto(Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            String jpql = "UPDATE TarefaSimples t " +
                    "SET t.emAberto = false " +
                    "WHERE t.projeto.id = :idProjeto";

            entityManager.flush();
            entityManager.createQuery(
                            jpql)
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
