package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Persona;

import java.util.List;


public class PersonaDAO extends AbstractDAO<Persona> {
    private final EntityManager entityManager;

    public PersonaDAO(EntityManager em) {
        super(Persona.class);
        this.entityManager = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}