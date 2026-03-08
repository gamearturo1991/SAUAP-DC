package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.AsignacionDocente;

import java.util.List;


public class AsignacionDocenteDAO extends AbstractDAO<AsignacionDocente> {
    private final EntityManager entityManager;

    public AsignacionDocenteDAO(EntityManager em) {
        super(AsignacionDocente.class);
        this.entityManager = em;
    }

    public List<AsignacionDocente> obtenerTodos(){
        return entityManager
                .createQuery("SELECT a FROM Profesor a", AsignacionDocente.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}