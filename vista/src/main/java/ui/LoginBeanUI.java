package ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.DC.entity.Usuario;
import mx.DC.negocio.UsuarioService;
import mx.DC.persistence.JPAUtil;

import java.io.IOException;
import java.io.Serializable;

@Named("loginUI")
@SessionScoped
public class LoginBeanUI implements Serializable {

    private String nombreUsuario;
    private String contrasena;
    private Usuario usuarioActual;

    public void login() throws IOException {
        try {
            // EntityManager fresco en cada intento para evitar estado estancado
            UsuarioService svc = new UsuarioService(JPAUtil.getEntityManager());
            Usuario u = svc.login(nombreUsuario, contrasena);
            if (u != null) {
                usuarioActual = u;
                // Guardar en sesión HTTP para que el AuthFilter pueda leerlo
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("usuarioLogueado", u);
                String destino = u.esAdministrador() ? "/registroAsignatura.xhtml" : "/perfil.xhtml";
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(FacesContext.getCurrentInstance().getExternalContext()
                                .getRequestContextPath() + destino);
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Usuario o contrasena incorrectos", null));
            }
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al iniciar sesion: " + ex.getMessage(), null));
        }
    }

    public void logout() throws IOException {
        usuarioActual = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(FacesContext.getCurrentInstance().getExternalContext()
                        .getRequestContextPath() + "/login.xhtml");
    }

    public boolean isLoggedIn() { return usuarioActual != null; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String v) { this.nombreUsuario = v; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String v) { this.contrasena = v; }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public void setUsuarioActual(Usuario v) { this.usuarioActual = v; }
}
