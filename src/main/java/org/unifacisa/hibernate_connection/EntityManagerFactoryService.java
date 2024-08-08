package org.unifacisa.hibernate_connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryService {

    private EntityManagerFactory entityManagerFactory;

    public EntityManagerFactoryService(){
    }

    public EntityManagerFactory entityManagerFactory() {

        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        }
        return entityManagerFactory;
    }

    public void inicializarEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        }
    }

    public void fechaEntityManagerFactory() {

        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

}
