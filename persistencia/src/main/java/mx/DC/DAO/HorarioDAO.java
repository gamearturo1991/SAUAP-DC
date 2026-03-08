package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Horario;

import java.util.List;


public class HorarioDAO extends AbstractDAO<Horario> {
    private final EntityManager entityManager;

    public HorarioDAO(EntityManager em) {
        super(Horario.class);
        this.entityManager = em;
    }

    public List<Horario> obtenerTodos(){
        return entityManager
                .createQuery("SELECT a FROM Profesor a", Horario.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}