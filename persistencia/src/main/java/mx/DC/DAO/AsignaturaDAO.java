package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mx.DC.entity.Asignatura;
import mx.DC.persistence.AbstractDAO;

import java.util.List;

public class AsignaturaDAO extends AbstractDAO<Asignatura> {

    private final EntityManager entityManager;

    public AsignaturaDAO(EntityManager em) {
        super(Asignatura.class);
        this.entityManager = em;
    }

    public List<Asignatura> obtenerTodos() {
        return entityManager
                .createQuery("SELECT a FROM Asignatura a", Asignatura.class)
                .getResultList();
    }

    public Asignatura findByNombre(String nombre) {
        try {
            return entityManager
                    .createQuery("SELECT a FROM Asignatura a WHERE a.nombre = :nombre", Asignatura.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Asignatura> findByNombreLike(String nombre) {
        return entityManager
                .createQuery("SELECT a FROM Asignatura a WHERE LOWER(a.nombre) LIKE LOWER(:nombre)", Asignatura.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
