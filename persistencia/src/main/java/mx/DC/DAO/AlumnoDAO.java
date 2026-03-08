package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Alumno;

import java.util.List;


public class AlumnoDAO extends AbstractDAO<Alumno> {
    private final EntityManager entityManager;

    public AlumnoDAO(EntityManager em) {
        super(Alumno.class);
        this.entityManager = em;
    }

    public List<Alumno> obtenerTodos(){
        return entityManager
                .createQuery("SELECT a FROM Alumno a", Alumno.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
