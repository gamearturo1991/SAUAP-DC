package ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.DC.entity.AsignacionDocente;
import mx.DC.entity.Asignatura;
import mx.DC.entity.Grupo;
import mx.DC.entity.Persona;
import mx.DC.entity.Profesor;
import mx.DC.facade.FacadeAsignacionDocente;
import mx.DC.facade.FacadeAsignatura;
import mx.DC.facade.FacadeGrupo;
import mx.DC.facade.FacadeProfesor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named("asignacionesUI")
@SessionScoped
public class AsignacionesUI implements Serializable {

    private final FacadeAsignacionDocente facadeAsignacionDocente;
    private final FacadeProfesor facadeProfesor;
    private final FacadeAsignatura facadeAsignatura;
    private final FacadeGrupo facadeGrupo;

    private List<Profesor> profesores;
    private List<Asignatura> asignaturas;
    private List<Grupo> grupos;
    private List<AsignacionDocente> asignaciones;

    private Integer profesorIdSeleccionado;
    private Integer asignaturaIdSeleccionada;
    private Integer grupoIdSeleccionado;

    public AsignacionesUI() {
        this.facadeAsignacionDocente = new FacadeAsignacionDocente();
        this.facadeProfesor = new FacadeProfesor();
        this.facadeAsignatura = new FacadeAsignatura();
        this.facadeGrupo = new FacadeGrupo();
    }

    @PostConstruct
    public void init() {
        recargarTodo();
    }

    public void recargarTodo() {
        profesores = safeList(facadeProfesor.obtenerTodos());
        asignaturas = safeList(facadeAsignatura.obtenerTodos());
        grupos = safeList(facadeGrupo.obtenerTodos());
        asignaciones = safeList(facadeAsignacionDocente.obtenerTodos());

        if (!profesores.isEmpty() && profesorIdSeleccionado == null) {
            profesorIdSeleccionado = profesores.get(0).getId();
        }
        if (!asignaturas.isEmpty() && asignaturaIdSeleccionada == null) {
            asignaturaIdSeleccionada = asignaturas.get(0).getId();
        }
        if (!grupos.isEmpty() && grupoIdSeleccionado == null) {
            grupoIdSeleccionado = grupos.get(0).getId();
        }
    }

    public void guardarAsignacion() {
        try {
            Profesor profesor = findProfesorById(profesorIdSeleccionado);
            Asignatura asignatura = findAsignaturaById(asignaturaIdSeleccionada);
            Grupo grupo = findGrupoById(grupoIdSeleccionado);

            if (profesor == null || asignatura == null || grupo == null) {
                addWarn("Selecciona profesor, asignatura y grupo.");
                return;
            }

            AsignacionDocente nueva = new AsignacionDocente();
            nueva.setIdProfesor(profesor);
            nueva.setIdAsignatura(asignatura);
            nueva.setIdGrupo(grupo);
            nueva.setFechaAsignacion(LocalDate.now());
            nueva.setEstadoAsignacion("activa");

            facadeAsignacionDocente.guardar(nueva);
            asignaciones = safeList(facadeAsignacionDocente.obtenerTodos());
            addInfo("Asignacion guardada correctamente.");
        } catch (Exception ex) {
            addError("No fue posible guardar la asignacion.", ex.getMessage());
        }
    }

    public void seleccionarProfesor(Integer idProfesor) {
        profesorIdSeleccionado = idProfesor;
    }

    public void seleccionarAsignatura(Integer idAsignatura) {
        asignaturaIdSeleccionada = idAsignatura;
    }

    public String nombreProfesor(Profesor profesor) {
        if (profesor == null || profesor.getIdPersona() == null) {
            return "Sin datos";
        }
        Persona p = profesor.getIdPersona();
        return ((p.getNombre() == null ? "" : p.getNombre()) + " "
                + (p.getApellidoPaterno() == null ? "" : p.getApellidoPaterno())).trim();
    }

    public int horasTotalesAsignatura(Asignatura asignatura) {
        if (asignatura == null) {
            return 0;
        }
        int clase = asignatura.getNumHorasClase() != null ? asignatura.getNumHorasClase() : 0;
        int taller = asignatura.getNumHorasTaller() != null ? asignatura.getNumHorasTaller() : 0;
        int lab = asignatura.getNumHorasLaboratorio() != null ? asignatura.getNumHorasLaboratorio() : 0;
        return clase + taller + lab;
    }

    public String etiquetaGrupo(Grupo grupo) {
        if (grupo == null) {
            return "Sin grupo";
        }
        return "Grupo " + grupo.getNumeroGrupo() + "-" + grupo.getCodigoGrupo();
    }

    private Profesor findProfesorById(Integer id) {
        if (id == null) {
            return null;
        }
        for (Profesor profesor : profesores) {
            if (id.equals(profesor.getId())) {
                return profesor;
            }
        }
        return null;
    }

    private Asignatura findAsignaturaById(Integer id) {
        if (id == null) {
            return null;
        }
        for (Asignatura asignatura : asignaturas) {
            if (id.equals(asignatura.getId())) {
                return asignatura;
            }
        }
        return null;
    }

    private Grupo findGrupoById(Integer id) {
        if (id == null) {
            return null;
        }
        for (Grupo grupo : grupos) {
            if (id.equals(grupo.getId())) {
                return grupo;
            }
        }
        return null;
    }

    private <T> List<T> safeList(List<T> input) {
        return input == null ? new ArrayList<>() : input;
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

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public List<AsignacionDocente> getAsignaciones() {
        return asignaciones;
    }

    public Integer getProfesorIdSeleccionado() {
        return profesorIdSeleccionado;
    }

    public void setProfesorIdSeleccionado(Integer profesorIdSeleccionado) {
        this.profesorIdSeleccionado = profesorIdSeleccionado;
    }

    public Integer getAsignaturaIdSeleccionada() {
        return asignaturaIdSeleccionada;
    }

    public void setAsignaturaIdSeleccionada(Integer asignaturaIdSeleccionada) {
        this.asignaturaIdSeleccionada = asignaturaIdSeleccionada;
    }

    public Integer getGrupoIdSeleccionado() {
        return grupoIdSeleccionado;
    }

    public void setGrupoIdSeleccionado(Integer grupoIdSeleccionado) {
        this.grupoIdSeleccionado = grupoIdSeleccionado;
    }
}
