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

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}