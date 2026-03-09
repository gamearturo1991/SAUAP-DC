package ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.DC.entity.Asignatura;
import mx.DC.facade.FacadeAsignatura;

import java.io.Serializable;
import java.util.List;

@Named("asignaturasUI")
@SessionScoped
public class AsignaturasUI implements Serializable {

    private FacadeAsignatura facadeAsignatura;
    private List<Asignatura> asignaturas;
    private Asignatura asignaturaSelecionada;
    private Asignatura asignaturaNueva;
    private boolean mostrarDialogoNueva = false;
    private boolean mostrarDialogoEditar = false;

    public AsignaturasUI() {
        this.facadeAsignatura = new FacadeAsignatura();
    }

    @PostConstruct
    public void init() {
        asignaturaNueva = new Asignatura();
        cargarAsignaturas();
    }

    /**
     * Carga todas las asignaturas desde la base de datos
     */
    public void cargarAsignaturas() {
        try {
            this.asignaturas = facadeAsignatura.obtenerTodos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al cargar asignaturas", e.getMessage()));
        }
    }

    /**
     * Abre el diálogo para crear una nueva asignatura
     */
    public void abrirDialogoNueva() {
        asignaturaNueva = new Asignatura();
        mostrarDialogoNueva = true;
    }

    /**
     * Abre el diálogo para editar una asignatura existente
     */
    public void abrirDialogoEditar(Asignatura asignatura) {
        this.asignaturaSelecionada = new Asignatura();
        this.asignaturaSelecionada.setId(asignatura.getId());
        this.asignaturaSelecionada.setNombre(asignatura.getNombre());
        this.asignaturaSelecionada.setNumHorasClase(asignatura.getNumHorasClase());
        this.asignaturaSelecionada.setNumHorasTaller(asignatura.getNumHorasTaller());
        this.asignaturaSelecionada.setNumHorasLaboratorio(asignatura.getNumHorasLaboratorio());
        mostrarDialogoEditar = true;
    }

    /**
     * Cierra los diálogos
     */
    public void cerrarDialogos() {
        mostrarDialogoNueva = false;
        mostrarDialogoEditar = false;
        asignaturaSelecionada = null;
        asignaturaNueva = null;
    }

    /**
     * Guarda una nueva asignatura
     */
    public void guardarAsignatura() {
        try {
            if (asignaturaNueva == null || asignaturaNueva.getNombre() == null || asignaturaNueva.getNombre().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "El nombre de la asignatura es requerido", ""));
                return;
            }
            if (asignaturaNueva.getNumHorasClase() == null || asignaturaNueva.getNumHorasClase() < 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ingresa un número válido de horas de clase", ""));
                return;
            }
            if (asignaturaNueva.getNumHorasTaller() == null || asignaturaNueva.getNumHorasTaller() < 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ingresa un número válido de horas de taller", ""));
                return;
            }
            if (asignaturaNueva.getNumHorasLaboratorio() == null || asignaturaNueva.getNumHorasLaboratorio() < 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ingresa un número válido de horas de laboratorio", ""));
                return;
            }

            facadeAsignatura.guardar(asignaturaNueva);
            cargarAsignaturas();
            cerrarDialogos();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Asignatura guardada correctamente", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al guardar asignatura", e.getMessage()));
        }
    }

    /**
     * Actualiza una asignatura existente
     */
    public void actualizarAsignatura() {
        try {
            if (asignaturaSelecionada == null || asignaturaSelecionada.getNombre() == null || asignaturaSelecionada.getNombre().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "El nombre de la asignatura es requerido", ""));
                return;
            }
            if (asignaturaSelecionada.getNumHorasClase() == null || asignaturaSelecionada.getNumHorasClase() < 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ingresa un número válido de horas de clase", ""));
                return;
            }
            if (asignaturaSelecionada.getNumHorasTaller() == null || asignaturaSelecionada.getNumHorasTaller() < 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ingresa un número válido de horas de taller", ""));
                return;
            }
            if (asignaturaSelecionada.getNumHorasLaboratorio() == null || asignaturaSelecionada.getNumHorasLaboratorio() < 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ingresa un número válido de horas de laboratorio", ""));
                return;
            }

            facadeAsignatura.actualizar(asignaturaSelecionada);
            cargarAsignaturas();
            cerrarDialogos();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Asignatura actualizada correctamente", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al actualizar asignatura", e.getMessage()));
        }
    }

    /**
     * Elimina una asignatura
     */
    public void eliminarAsignatura(Asignatura asignatura) {
        try {
            facadeAsignatura.eliminar(asignatura);
            cargarAsignaturas();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Asignatura eliminada correctamente", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al eliminar asignatura", e.getMessage()));
        }
    }

    // Getters y Setters
    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public Asignatura getAsignaturaSelecionada() {
        return asignaturaSelecionada;
    }

    public void setAsignaturaSelecionada(Asignatura asignaturaSelecionada) {
        this.asignaturaSelecionada = asignaturaSelecionada;
    }

    public Asignatura getAsignaturaNueva() {
        return asignaturaNueva;
    }

    public void setAsignaturaNueva(Asignatura asignaturaNueva) {
        this.asignaturaNueva = asignaturaNueva;
    }

    public boolean isMostrarDialogoNueva() {
        return mostrarDialogoNueva;
    }

    public void setMostrarDialogoNueva(boolean mostrarDialogoNueva) {
        this.mostrarDialogoNueva = mostrarDialogoNueva;
    }

    public boolean isMostrarDialogoEditar() {
        return mostrarDialogoEditar;
    }

    public void setMostrarDialogoEditar(boolean mostrarDialogoEditar) {
        this.mostrarDialogoEditar = mostrarDialogoEditar;
    }
}
