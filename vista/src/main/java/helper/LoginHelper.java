package helper;

import mx.DC.entity.Usuario;
import mx.DC.integration.ServiceFacadeLocator;

import java.io.Serializable;

public class LoginHelper implements Serializable {

    /**
     * Login: busca el usuario por nombreUsuario y verifica contraseña
     */
    public Usuario Login(String nombreUsuario, String password) {
        return ServiceFacadeLocator.getInstanceFacadeUsuario().login(nombreUsuario, password);
    }
}
