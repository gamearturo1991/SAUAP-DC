package ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.DC.entity.Profesor;
import mx.DC.entity.Usuario;
import mx.DC.facade.FacadeProfesor;
import mx.DC.facade.FacadeUsuario;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Named("crearUsuarioProfesoresUI")
@SessionScoped
public class CrearUsuarioProfesoresUI implements Serializable {

    private final FacadeProfesor facadeProfesor;
    private final FacadeUsuario facadeUsuario;

    private List<Profesor> profesores;
    private Profesor profesorSeleccionado;
    private String nombreUsuario;
    private String contrasena;
    private String contrasenaConfirm;

    public CrearUsuarioProfesoresUI() {
        this.facadeProfesor = new FacadeProfesor();
        this.facadeUsuario = new FacadeUsuario();
    }

    @PostConstruct
    public void init() {
        cargarProfesores();
    }

    public void cargarProfesores() {
        try {
            this.profesores = facadeProfesor.obtenerTodos();
            if (profesores == null) {
                profesores = new ArrayList<>();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al cargar profesores", e.getMessage()));
        }
    }

    public void guardarUsuario() {
        try {
            // Validaciones
            if (profesorSeleccionado == null) {
                addWarn("Selecciona un profesor");
                return;
            }
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                addWarn("Ingresa nombre de usuario");
                return;
            }
            if (contrasena == null || contrasena.trim().isEmpty()) {
                addWarn("Ingresa contraseña");
                return;
            }
            if (!contrasena.equals(contrasenaConfirm)) {
                addWarn("Las contraseñas no coinciden");
                return;
            }
            if (contrasena.length() < 6) {
                addWarn("La contraseña debe tener al menos 6 caracteres");
                return;
            }

            // Crear usuario
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(nombreUsuario.trim());
            usuario.setContrasena(hashPassword(contrasena));
            usuario.setRol("profesor");
            usuario.setIdProfesor(profesorSeleccionado);

            facadeUsuario.saveUsuario(usuario);

            // Limpiar formulario
            limpiarFormulario();
            cargarProfesores();

            addInfo("Usuario creado exitosamente para " + nombreProfesor(profesorSeleccionado));
        } catch (Exception e) {
            addError("Error al crear usuario", e.getMessage());
        }
    }

    public void limpiarFormulario() {
        profesorSeleccionado = null;
        nombreUsuario = null;
        contrasena = null;
        contrasenaConfirm = null;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return password; // Si falla, guardar en texto plano (no recomendado)
        }
    }

    private String nombreProfesor(Profesor profesor) {
        if (profesor == null || profesor.getIdPersona() == null) {
            return "Profesor";
        }
        return profesor.getIdPersona().getNombre() + " " + profesor.getIdPersona().getApellidoPaterno();
    }

    private void addInfo(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    private void addWarn(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
    }

    private void addError(String message, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail));
    }

    // Getters y Setters
    public List<Profesor> getProfesores() {
        return profesores;
    }

    public Profesor getProfesorSeleccionado() {
        return profesorSeleccionado;
    }

    public void setProfesorSeleccionado(Profesor profesorSeleccionado) {
        this.profesorSeleccionado = profesorSeleccionado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasenaConfirm() {
        return contrasenaConfirm;
    }

    public void setContrasenaConfirm(String contrasenaConfirm) {
        this.contrasenaConfirm = contrasenaConfirm;
    }
}

