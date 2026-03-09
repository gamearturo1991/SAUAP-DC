package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mx.DC.entity.Profesor;
import mx.DC.negocio.ProfesorService;
import mx.DC.persistence.JPAUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("docenteUI")
@ViewScoped
public class DocenteBeanUI implements Serializable {

    private String rfc;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoContrato = "planta";

    private boolean crearUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String confirmacionContrasena;

    private List<Profesor> docentes = new ArrayList<>();
    private ProfesorService profesorService;

    @Inject
    private LoginBeanUI loginUI;

    @PostConstruct
    public void init() {
        profesorService = new ProfesorService(JPAUtil.getEntityManager());
        cargarDocentes();
    }

    public void registrar() {
        try {
            if (crearUsuario) {
                profesorService.registrarDocenteConUsuario(
                        rfc,
                        nombre,
                        apellidoPaterno,
                        apellidoMaterno,
                        tipoContrato,
                        nombreUsuario,
                        contrasena,
                        confirmacionContrasena
                );
            } else {
                profesorService.registrarDocente(rfc, nombre, apellidoPaterno, apellidoMaterno, tipoContrato);
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Docente registrado correctamente", null));
            limpiar();
            cargarDocentes();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void limpiar() {
        rfc = null;
        nombre = null;
        apellidoPaterno = null;
        apellidoMaterno = null;
        tipoContrato = "planta";
        crearUsuario = false;
        nombreUsuario = null;
        contrasena = null;
        confirmacionContrasena = null;
    }

    private void cargarDocentes() {
        docentes = profesorService.obtenerTodos();
    }

    public boolean isAdministrador() {
        return loginUI != null
                && loginUI.getUsuarioActual() != null
                && loginUI.getUsuarioActual().esAdministrador();
    }

    // Getters / Setters
    public String getRfc() { return rfc; }
    public void setRfc(String v) { this.rfc = v; }
    public String getNombre() { return nombre; }
    public void setNombre(String v) { this.nombre = v; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String v) { this.apellidoPaterno = v; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String v) { this.apellidoMaterno = v; }
    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String v) { this.tipoContrato = v; }
    public List<Profesor> getDocentes() { return docentes; }

    public boolean isCrearUsuario() { return crearUsuario; }
    public void setCrearUsuario(boolean crearUsuario) { this.crearUsuario = crearUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getConfirmacionContrasena() { return confirmacionContrasena; }
    public void setConfirmacionContrasena(String confirmacionContrasena) { this.confirmacionContrasena = confirmacionContrasena; }
}
