package mx.DC.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mx.DC.persistence.AbstractDAO;
import mx.DC.entity.Usuario;

import java.util.List;

public class UsuarioDAO extends AbstractDAO<Usuario> {
    private final EntityManager entityManager;

    public UsuarioDAO(EntityManager em) {
        super(Usuario.class);
        this.entityManager = em;
    }

    public Usuario findByNombreUsuario(String nombreUsuario) {
        try {
            return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombre", Usuario.class)
                    .setParameter("nombre", nombreUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
