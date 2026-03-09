package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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

    private List<Profesor> docentes = new ArrayList<>();
    private ProfesorService profesorService;

    @PostConstruct
    public void init() {
        profesorService = new ProfesorService(JPAUtil.getEntityManager());
        cargarDocentes();
    }

    public void registrar() {
        try {
            profesorService.registrarDocente(rfc, nombre, apellidoPaterno, apellidoMaterno, tipoContrato);
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
        rfc = null; nombre = null; apellidoPaterno = null; apellidoMaterno = null; tipoContrato = "planta";
    }

    private void cargarDocentes() {
        docentes = profesorService.obtenerTodos();
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
}
