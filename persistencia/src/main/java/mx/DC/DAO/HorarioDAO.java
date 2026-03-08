package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.entity.AsignacionDocente;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Horario;

import java.util.List;


public class HorarioDAO extends AbstractDAO<Horario> {
    private final EntityManager entityManager;

    public HorarioDAO(EntityManager em) {
        super(Horario.class);
        this.entityManager = em;
    }

    public List<Horario> findByAsignacion(AsignacionDocente asignacion) {
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT h FROM Horario h WHERE h.idAsignacionDocente = :asignacion", Horario.class)
                .setParameter("asignacion", asignacion)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}