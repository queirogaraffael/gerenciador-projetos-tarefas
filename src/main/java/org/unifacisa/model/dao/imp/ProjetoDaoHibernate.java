package org.unifacisa.model.dao.imp;

import org.unifacisa.exceptions.GlobalExceptionHandler;
import org.unifacisa.model.DTOs.ProjetoDTO;
import org.unifacisa.model.dao.ProjetoDao;
import org.unifacisa.model.entities.Projeto;

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
    public Projeto getProjetoById(Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT projeto FROM Projeto projeto WHERE projeto.id =: idProjeto", Projeto.class).setParameter("idProjeto", idProjeto).getSingleResult();

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
    public boolean verificaSeHaProjetoComMesmoTitulo(String titulo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.createQuery("SELECT projeto FROM Projeto projeto WHERE LOWER(projeto.titulo) = LOWER(:titulo)", Projeto.class)
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
    public List<ProjetoDTO> getProjetosDTO() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery(
                    "SELECT new org.unifacisa.model.DTOs.ProjetoDTO(projeto.id, projeto.titulo) FROM Projeto projeto",
                    ProjetoDTO.class).getResultList();

        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException(error);
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<ProjetoDTO> getProjetosDTOPorNome(String titulo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT new org.unifacisa.model.DTOs.ProjetoDTO(projeto.id, projeto.titulo) FROM Projeto projeto WHERE LOWER(projeto.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))", ProjetoDTO.class).setParameter("titulo", titulo).getResultList();

        } catch (Exception error) {
            GlobalExceptionHandler.handleGeneralException("Sem projeto(s) com esse nome.");
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }

    }


    @Override
    public void atualizaProjetoById(Projeto projetoModificado) {
        if (projetoModificado == null || projetoModificado.getId() == null) {
            GlobalExceptionHandler.handleIllegalArgumentException("Projeto ou ID inválido.");
            return;
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Projeto projetoExistente = entityManager.find(Projeto.class, projetoModificado.getId());
            if (projetoExistente != null) {
                projetoExistente.setTitulo(projetoModificado.getTitulo());
                projetoExistente.setDescricao(projetoModificado.getDescricao());
                projetoExistente.setEmAberto(projetoModificado.isEmAberto());

                transaction.commit();
            } else {
                GlobalExceptionHandler.handleRuntimeException("Projeto não encontrado para atualização.");
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
    public void deletaProjetoById(Long idProjeto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Projeto projeto = entityManager.find(Projeto.class, idProjeto);

            if (projeto != null) {
                entityManager.remove(projeto);
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


}
