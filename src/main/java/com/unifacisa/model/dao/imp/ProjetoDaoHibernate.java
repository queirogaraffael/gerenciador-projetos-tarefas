package com.unifacisa.model.dao.imp;

import com.unifacisa.dtos.ProjetoDTO;
import com.unifacisa.exceptions.GlobalExceptionHandler;
import com.unifacisa.model.dao.ProjetoDao;
import com.unifacisa.model.domain.entities.Projeto;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

public class ProjetoDaoHibernate implements ProjetoDao {

    private final EntityManagerFactory entityManagerFactory;

    public ProjetoDaoHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public void criaProjeto(Projeto projeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(projeto);
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
    public Projeto getProjetoById(Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT p " +
                    "FROM Projeto p " +
                    "WHERE p.id =: idProjeto";

            return entityManager.createQuery(jpql, Projeto.class).setParameter("idProjeto", idProjeto).getSingleResult();

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
    public boolean haProjetoComMesmoTitulo(String titulo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT p " +
                    "FROM Projeto p " +
                    "WHERE LOWER(p.titulo) = LOWER(:titulo)";

            entityManager.createQuery(jpql, Projeto.class)
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
    public List<ProjetoDTO> getProjetosDTO() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT new com.unifacisa.dtos.ProjetoDTO(p.id, p.titulo) " +
                    "FROM Projeto p";

            return entityManager.createQuery(
                    jpql,
                    ProjetoDTO.class).getResultList();

        } catch (Exception e) {
            GlobalExceptionHandler.handleGeneralException("Problemas ao buscar projetos: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<ProjetoDTO> getProjetosDTOPorNome(String titulo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT new com.unifacisa.dtos.ProjetoDTO(p.id, p.titulo) " +
                    "FROM Projeto p " +
                    "WHERE LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))";

            return entityManager.createQuery(jpql, ProjetoDTO.class).setParameter("titulo", titulo).getResultList();

        } catch (Exception e) {
            GlobalExceptionHandler.handleGeneralException("Problemas ao buscar projetos: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }

    }


    @Override
    public void atualizaProjetoById(Projeto projetoModificado) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            String jpql = "SELECT p " +
                    "FROM Projeto p " +
                    "WHERE p.id =: idProjeto";

            Projeto projetoExistente = entityManager.createQuery(jpql, Projeto.class).setParameter("idProjeto", projetoModificado.getId()).getSingleResult();

            projetoExistente.setTitulo(projetoModificado.getTitulo());
            projetoExistente.setDescricao(projetoModificado.getDescricao());
            projetoExistente.setEmAberto(projetoModificado.isEmAberto());

            entityManager.merge(projetoExistente);

            transaction.commit();

        } catch (NoResultException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handleGeneralException("Projeto nao encontrado para modificacao.");
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
    public void deletaProjetoById(Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            String jpql = "SELECT p " +
                    "FROM Projeto p " +
                    "WHERE p.id =: idProjeto";

            Projeto projeto = entityManager.createQuery(jpql, Projeto.class).setParameter("idProjeto", idProjeto).getSingleResult();

            entityManager.remove(projeto);
            transaction.commit();

        } catch (NoResultException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            GlobalExceptionHandler.handleGeneralException("Projeto nao encontrado para delecao.");
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
