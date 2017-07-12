package com.mySampleApplication.server.dao;

/**
 * Created by bogdan on 7/6/17.
 */

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.io.Serializable;


public abstract class AbstractDao<T> implements Serializable {
    protected Class<T> entityClass;

    @Inject
    protected Provider<EntityManager> em;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return this.em.get();
    }

    @Transactional
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Transactional
    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    @Transactional
    public void remove(Long entityId) {
        T entity = find(entityId);

        if (entity != null)
            remove(entity);
    }

    @Transactional
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

}
