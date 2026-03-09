package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.entity.AsignacionDocente;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Horario;

import java.time.LocalTime;
import java.util.List;


public class HorarioDAO extends AbstractDAO<Horario> {
    private final EntityManager entityManager;

    public HorarioDAO(EntityManager em) {
        super(Horario.class);
        this.entityManager = em;
    }

    public List<Horario> obtenerTodos(){
        return entityManager
                .createQuery("SELECT h FROM Horario h", Horario.class)
                .getResultList();
    }

    public List<Horario> findByAsignacion(AsignacionDocente asignacion) {
        return entityManager.createQuery(
                "SELECT h FROM Horario h WHERE h.idAsignacionDocente = :asignacion", Horario.class)
                .setParameter("asignacion", asignacion)
                .getResultList();
    }

    /**
     * Retorna los bloques de horario del profesor que se traslapan con el rango dado.
     * Dos rangos se traslapan cuando: inicio1 < fin2 AND fin1 > inicio2
     */
    public List<Horario> findTraslapeProfesor(int idProfesor, String diaSemana,
                                               LocalTime horaInicio, LocalTime horaFin) {
        return entityManager.createQuery(
                "SELECT h FROM Horario h " +
                "WHERE h.idAsignacionDocente.idProfesor.id = :idProfesor " +
                "AND h.diaSemana = :dia " +
                "AND h.horaInicio < :horaFin " +
                "AND h.horaFin > :horaInicio",
                Horario.class)
                .setParameter("idProfesor", idProfesor)
                .setParameter("dia", diaSemana)
                .setParameter("horaInicio", horaInicio)
                .setParameter("horaFin", horaFin)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}