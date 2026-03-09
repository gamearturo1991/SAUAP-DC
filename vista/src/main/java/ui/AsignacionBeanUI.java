package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.DC.entity.Asignatura;
import mx.DC.entity.Grupo;
import mx.DC.entity.Profesor;
import mx.DC.negocio.AsignacionService;
import mx.DC.negocio.AsignaturaService;
import mx.DC.negocio.ProfesorService;
import mx.DC.persistence.JPAUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named("asignacionUI")
@ViewScoped
public class AsignacionBeanUI implements Serializable {

    private List<Profesor> profesores = new ArrayList<>();
    private List<Asignatura> asignaturas = new ArrayList<>();

    private Profesor profesorSeleccionado;
    private Asignatura asignaturaSeleccionada;

    private String filtroProfesores = "";
    private String filtroAsignaturas = "";
    private int idGrupo = 1;

    // Campos de horario
    private String diaSemana = "Lunes";
    private String horaInicio = "07:00";
    private String horaFin    = "08:00";
    private String aula       = "";

    private AsignacionService asignacionService;
    private ProfesorService profesorService;
    private AsignaturaService asignaturaService;

    @PostConstruct
    public void init() {
        var em = JPAUtil.getEntityManager();
        asignacionService = new AsignacionService(em);
        profesorService   = new ProfesorService(em);
        asignaturaService = new AsignaturaService(em);
        cargarDatos();
    }

    private void cargarDatos() {
        profesores  = profesorService.obtenerTodos();
        asignaturas = asignaturaService.obtenerTodas();
    }

    public void seleccionarProfesor(Profesor p) { this.profesorSeleccionado = p; }
    public void seleccionarAsignatura(Asignatura a) { this.asignaturaSeleccionada = a; }

    public void aceptar() {
        if (profesorSeleccionado == null || asignaturaSeleccionada == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Seleccione un profesor y una asignatura", null));
            return;
        }
        try {
            asignacionService.asignar(
                    profesorSeleccionado.getId(),
                    asignaturaSeleccionada.getId(),
                    idGrupo,
                    diaSemana,
                    horaInicio,
                    horaFin,
                    aula);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Asignacion realizada: " + profesorSeleccionado.getNombreCompleto()
                            + " — " + asignaturaSeleccionada.getNombre()
                            + " (" + diaSemana + " " + horaInicio + "-" + horaFin + ")", null));
            profesorSeleccionado  = null;
            asignaturaSeleccionada = null;
            horaInicio = "07:00";
            horaFin    = "08:00";
            aula       = "";
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Profesor> getProfesoresFiltrados() {
        if (filtroProfesores == null || filtroProfesores.isBlank()) return profesores;
        String f = filtroProfesores.toLowerCase();
        return profesores.stream()
                .filter(p -> p.getNombreCompleto().toLowerCase().contains(f)
                          || p.getRfc().toLowerCase().contains(f))
                .collect(Collectors.toList());
    }

    public List<Asignatura> getAsignaturasFiltradas() {
        if (filtroAsignaturas == null || filtroAsignaturas.isBlank()) return asignaturas;
        String f = filtroAsignaturas.toLowerCase();
        return asignaturas.stream()
                .filter(a -> a.getNombre().toLowerCase().contains(f))
                .collect(Collectors.toList());
    }

    // Getters / Setters
    public List<Profesor> getProfesores() { return profesores; }
    public List<Asignatura> getAsignaturas() { return asignaturas; }
    public Profesor getProfesorSeleccionado() { return profesorSeleccionado; }
    public Asignatura getAsignaturaSeleccionada() { return asignaturaSeleccionada; }
    public String getFiltroProfesores() { return filtroProfesores; }
    public void setFiltroProfesores(String v) { this.filtroProfesores = v; }
    public String getFiltroAsignaturas() { return filtroAsignaturas; }
    public void setFiltroAsignaturas(String v) { this.filtroAsignaturas = v; }
    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int v) { this.idGrupo = v; }
    public int getNumProfesores() { return profesores.size(); }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String v) { this.diaSemana = v; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String v) { this.horaInicio = v; }
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String v) { this.horaFin = v; }
    public String getAula() { return aula; }
    public void setAula(String v) { this.aula = v; }
}
