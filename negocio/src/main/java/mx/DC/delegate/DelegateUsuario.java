package mx.DC.delegate;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.UsuarioDAO;
import mx.DC.entity.Usuario;
import mx.DC.negocio.UsuarioService;
import mx.DC.persistence.HibernateUtil;

public class DelegateUsuario {

    public Usuario login(String nombreUsuario, String password) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            UsuarioService usuarioService = new UsuarioService(em);
            return usuarioService.login(nombreUsuario, password);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void saveUsuario(Usuario usuario) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(em);
            usuarioDAO.save(usuario);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}