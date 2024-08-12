package org.unifacisa.model.dao;

import org.unifacisa.model.dao.imp.ProjetoDaoHibernate;

import javax.persistence.EntityManagerFactory;

public class DaoFactory {

    private final EntityManagerFactory entityManagerFactory;

    public DaoFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public ProjetoDao createProjetoDao() {
        return new ProjetoDaoHibernate(entityManagerFactory);
    }
}
