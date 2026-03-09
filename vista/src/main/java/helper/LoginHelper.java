package helper;

import mx.DC.entity.Usuario;
import mx.DC.negocio.UsuarioService;
import mx.DC.persistence.JPAUtil;

import java.io.Serializable;

public class LoginHelper implements Serializable {

    public Usuario Login(String nombreUsuario, String password) {
        UsuarioService service = new UsuarioService(JPAUtil.getEntityManager());
        return service.login(nombreUsuario, password);
    }
}
