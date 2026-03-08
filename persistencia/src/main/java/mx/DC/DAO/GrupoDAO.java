package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.entity.Grupo;
import mx.DC.persistence.AbstractDAO;


import java.util.List;


public class GrupoDAO extends AbstractDAO<Grupo> {
    private final EntityManager entityManager;

    public GrupoDAO(EntityManager em) {
        super(Grupo.class);
        this.entityManager = em;
    }

    public List<Grupo> obtenerTodos(){
        return entityManager
                .createQuery("SELECT a FROM Profesor a", Grupo.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}