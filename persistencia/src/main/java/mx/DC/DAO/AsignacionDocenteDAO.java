package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.entity.Profesor;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.AsignacionDocente;

import java.util.List;


public class AsignacionDocenteDAO extends AbstractDAO<AsignacionDocente> {
    private final EntityManager entityManager;

    public AsignacionDocenteDAO(EntityManager em) {
        super(AsignacionDocente.class);
        this.entityManager = em;
    }

    public List<AsignacionDocente> findByProfesor(Profesor profesor) {
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT a FROM AsignacionDocente a WHERE a.idProfesor = :profesor", AsignacionDocente.class)
                .setParameter("profesor", profesor)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}