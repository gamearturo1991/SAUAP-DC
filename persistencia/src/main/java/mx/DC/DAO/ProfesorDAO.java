package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Profesor;

import java.util.List;


public class ProfesorDAO extends AbstractDAO<Profesor> {
    private final EntityManager entityManager;

    public ProfesorDAO(EntityManager em) {
        super(Profesor.class);
        this.entityManager = em;
    }

    public List<Profesor> obtenerTodos(){
        return entityManager
                .createQuery("SELECT a FROM Profesor a", Profesor.class)
                .getResultList();
    }
    public Profesor findByRFC(String rfc) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Profesor p WHERE p.rfc = :rfc", Profesor.class)
                    .setParameter("rfc", rfc)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
