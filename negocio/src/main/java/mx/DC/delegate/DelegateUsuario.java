package mx.DC.delegate;
import mx.DC.entity.Usuario;
import mx.DC.integration.ServiceLocator;
import mx.DC.DAO.UsuarioDAO;

public class DelegateUsuario {

    public Usuario login(String nombreUsuario, String password) {
        UsuarioDAO usuarioDAO = ServiceLocator.getInstanceUsuarioDAO();
        Usuario usuario = usuarioDAO.findByNombreUsuario(nombreUsuario);

        if (usuario != null && password.equals(usuario.getContrasena())) {
            return usuario;
        }
        return null;
    }

    public void saveUsuario(Usuario usuario) {
        UsuarioDAO usuarioDAO = ServiceLocator.getInstanceUsuarioDAO();
        usuarioDAO.save(usuario);
    }
}