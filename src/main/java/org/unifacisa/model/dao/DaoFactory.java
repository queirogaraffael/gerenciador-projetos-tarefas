package org.unifacisa.model.dao;

import org.unifacisa.model.dao.imp.ProjetoDaoHibernate;
import org.unifacisa.model.dao.imp.TarefaComPrazoDaoHibernate;
import org.unifacisa.model.dao.imp.TarefaSimplesDaoHibernate;

import javax.persistence.EntityManagerFactory;

public class DaoFactory {

    private final EntityManagerFactory entityManagerFactory;

    public DaoFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public TarefaSimplesDao createTarefaSimplesDao() {
        return new TarefaSimplesDaoHibernate(entityManagerFactory);
    }

    public TarefaComPrazoDao createTarefaComPrazoDao() {
        return new TarefaComPrazoDaoHibernate(entityManagerFactory);
    }
}
