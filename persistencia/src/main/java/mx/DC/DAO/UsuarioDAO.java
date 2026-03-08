package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Usuario;

import java.util.List;

public class UsuarioDAO extends AbstractDAO<Usuario> {
    private final EntityManager entityManager;

    public UsuarioDAO(EntityManager em) {
        super(Usuario.class);
        this.entityManager = em;
    }

    public List<Usuario> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Usuario u", Usuario.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
