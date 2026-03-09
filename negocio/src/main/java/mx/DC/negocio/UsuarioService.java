package mx.DC.negocio;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.UsuarioDAO;
import mx.DC.entity.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(EntityManager em) {
        this.usuarioDAO = new UsuarioDAO(em);
    }

    /** Retorna el usuario si las credenciales son correctas, null en caso contrario. */
    public Usuario login(String nombreUsuario, String contrasena) {
        if (nombreUsuario == null || contrasena == null) return null;
        Usuario u = usuarioDAO.findByNombreUsuario(nombreUsuario.trim());
        if (u == null) return null;

        String hashIngresado = md5(contrasena.trim());
        return hashIngresado.equalsIgnoreCase(u.getContrasenaHash().trim()) ? u : null;
    }

    /** Genera el hash MD5 en hexadecimal (minúsculas), igual que MySQL MD5(). */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 no disponible", e);
        }
    }
}
