package mx.DC.DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mx.DC.entity.Usuario;
import mx.DC.persistence.AbstractDAO;
public class UsuarioDAO extends AbstractDAO<Usuario> {
    private final EntityManager entityManager;
    public UsuarioDAO(EntityManager em) { super(Usuario.class); this.entityManager = em; }
    public Usuario findByNombreUsuario(String n) {
        try { return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :n", Usuario.class).setParameter("n", n).getSingleResult(); }
        catch (NoResultException e) { return null; }
    }
    @Override public EntityManager getEntityManager() { return entityManager; }
}
