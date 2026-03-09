package mx.DC.persistence;

import jakarta.persistence.EntityManager;

import java.util.List;

public abstract class AbstractDAO<T> {

    private final Class<T> entityClass;

    protected AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public T edit(T entity) {
        return getEntityManager().merge(entity);
    }

    public void destroy(T entity) {
        EntityManager em = getEntityManager();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        return getEntityManager()
                .createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public long count() {
        return (long) getEntityManager()
                .createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e")
                .getSingleResult();
    }
}
