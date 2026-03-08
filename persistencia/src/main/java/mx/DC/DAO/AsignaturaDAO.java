package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Asignatura;

import java.util.List;


public class AsignaturaDAO extends AbstractDAO<Asignatura> {
    private final EntityManager entityManager;

    public AsignaturaDAO(EntityManager em) {
        super(Asignatura.class);
        this.entityManager = em;
    }

    public List<Asignatura> obtenerTodos(){
        return entityManager
                .createQuery("SELECT a FROM Profesor a", Asignatura.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}