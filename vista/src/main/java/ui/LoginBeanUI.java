package ui;

import helper.LoginHelper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.DC.entity.Usuario;

import java.io.IOException;
import java.io.Serializable;

@Named("loginUI")
@SessionScoped
public class LoginBeanUI implements Serializable {

    private LoginHelper loginHelper;
    private Usuario usuario;

    public LoginBeanUI() {
        loginHelper = new LoginHelper();
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public void login() throws IOException {
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()
                || usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Ingresa usuario y contraseña.", null));
            return;
        }

        Usuario encontrado = loginHelper.Login(usuario.getNombreUsuario(), usuario.getContrasena());

        if (encontrado != null && encontrado.getId() != null) {
            usuario = encontrado;
            String contextPath = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestContextPath();

            // Redirigir según rol
            if ("administrador".equalsIgnoreCase(usuario.getRol())) {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(contextPath + "/asignaturas.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(contextPath + "/index.xhtml");
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Usuario o contraseña incorrectos.", "Intente de nuevo"));
        }
    }

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        String contextPath = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(contextPath + "/login.xhtml");
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
