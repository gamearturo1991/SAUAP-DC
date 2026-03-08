package mx.DC.facade;

import mx.DC.delegate.DelegateUsuario;
import mx.DC.entity.Usuario;

public class FacadeUsuario {

    private final DelegateUsuario delegateUsuario;

    public FacadeUsuario() {
        this.delegateUsuario = new DelegateUsuario();
    }

    public Usuario login(String nombreUsuario, String password) {
        return delegateUsuario.login(nombreUsuario, password);
    }

    public void saveUsuario(Usuario usuario) {
        delegateUsuario.saveUsuario(usuario);
    }
}