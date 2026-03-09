package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.DC.entity.Asignatura;
import mx.DC.negocio.AsignaturaService;
import mx.DC.persistence.JPAUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("asignaturaUI")
@ViewScoped
public class AsignaturaBeanUI implements Serializable {

    private String nombre;
    private int numHorasClase;
    private int numHorasTaller;
    private int numHorasLaboratorio;

    private List<Asignatura> asignaturas = new ArrayList<>();
    private AsignaturaService asignaturaService;

    @PostConstruct
    public void init() {
        asignaturaService = new AsignaturaService(JPAUtil.getEntityManager());
        cargarAsignaturas();
    }

    public void registrar() {
        try {
            asignaturaService.registrarAsignatura(nombre, numHorasClase, numHorasTaller, numHorasLaboratorio);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignatura registrada correctamente", null));
            limpiar();
            cargarAsignaturas();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void limpiar() {
        nombre = null;
        numHorasClase = 0;
        numHorasTaller = 0;
        numHorasLaboratorio = 0;
    }

    private void cargarAsignaturas() {
        asignaturas = asignaturaService.obtenerTodas();
    }

    public int getTotalHoras() { return numHorasClase + numHorasTaller + numHorasLaboratorio; }

    // Getters / Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getNumHorasClase() { return numHorasClase; }
    public void setNumHorasClase(int v) { this.numHorasClase = v; }
    public int getNumHorasTaller() { return numHorasTaller; }
    public void setNumHorasTaller(int v) { this.numHorasTaller = v; }
    public int getNumHorasLaboratorio() { return numHorasLaboratorio; }
    public void setNumHorasLaboratorio(int v) { this.numHorasLaboratorio = v; }
    public List<Asignatura> getAsignaturas() { return asignaturas; }
}
