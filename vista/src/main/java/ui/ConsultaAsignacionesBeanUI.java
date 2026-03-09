package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.DC.entity.AsignacionDocente;
import mx.DC.entity.Horario;
import mx.DC.entity.Profesor;
import mx.DC.negocio.AsignacionService;
import mx.DC.negocio.ProfesorService;
import mx.DC.persistence.JPAUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Named("consultaUI")
@ViewScoped
public class ConsultaAsignacionesBeanUI implements Serializable {

    /** Fila plana: un horario por registro (si la asignación tiene varios horarios, una fila por horario). */
    public static class FilaAsignacion {
        private final String nombreProfesor;
        private final String rfc;
        private final String tipoContrato;
        private final String asignatura;
        private final int totalHoras;
        private final String diaSemana;
        private final String horaInicio;
        private final String horaFin;
        private final String aula;

        public FilaAsignacion(String nombreProfesor, String rfc, String tipoContrato,
                              String asignatura, int totalHoras,
                              String diaSemana, String horaInicio, String horaFin, String aula) {
            this.nombreProfesor = nombreProfesor;
            this.rfc            = rfc;
            this.tipoContrato   = tipoContrato;
            this.asignatura     = asignatura;
            this.totalHoras     = totalHoras;
            this.diaSemana      = diaSemana;
            this.horaInicio     = horaInicio;
            this.horaFin        = horaFin;
            this.aula           = aula;
        }

        public String getNombreProfesor() { return nombreProfesor; }
        public String getRfc()            { return rfc; }
        public String getTipoContrato()   { return tipoContrato; }
        public String getAsignatura()     { return asignatura; }
        public int    getTotalHoras()     { return totalHoras; }
        public String getDiaSemana()      { return diaSemana; }
        public String getHoraInicio()     { return horaInicio; }
        public String getHoraFin()        { return horaFin; }
        public String getAula()           { return aula != null ? aula : ""; }
    }

    private List<FilaAsignacion> todasLasFilas  = new ArrayList<>();
    private List<FilaAsignacion> filasFiltradas = new ArrayList<>();
    private String buscarProfesor  = "";
    private String buscarAsignatura = "";

    @PostConstruct
    public void init() {
        cargarDatos();
    }

    private void cargarDatos() {
        todasLasFilas.clear();

        ProfesorService  profesorService  = new ProfesorService(JPAUtil.getEntityManager());
        AsignacionService asignacionService = new AsignacionService(JPAUtil.getEntityManager());

        List<Profesor> profesores = profesorService.obtenerTodos();
        // Ordenar por nombre completo
        profesores.sort(Comparator.comparing(Profesor::getNombreCompleto, String.CASE_INSENSITIVE_ORDER));

        for (Profesor prof : profesores) {
            List<AsignacionDocente> asignaciones = asignacionService.obtenerPorProfesor(prof);
            if (asignaciones.isEmpty()) {
                // Profesor sin asignaciones: mostrar una fila con guiones
                todasLasFilas.add(new FilaAsignacion(
                        prof.getNombreCompleto(), prof.getRfc(), prof.getTipoContrato(),
                        "—", 0, "—", "—", "—", null));
            } else {
                for (AsignacionDocente ad : asignaciones) {
                    List<Horario> horarios = asignacionService.obtenerHorariosPorAsignacion(ad);
                    if (horarios.isEmpty()) {
                        todasLasFilas.add(new FilaAsignacion(
                                prof.getNombreCompleto(), prof.getRfc(), prof.getTipoContrato(),
                                ad.getIdAsignatura().getNombre(),
                                ad.getTotalHorasSemanales(),
                                "—", "—", "—", null));
                    } else {
                        for (Horario h : horarios) {
                            todasLasFilas.add(new FilaAsignacion(
                                    prof.getNombreCompleto(), prof.getRfc(), prof.getTipoContrato(),
                                    ad.getIdAsignatura().getNombre(),
                                    ad.getTotalHorasSemanales(),
                                    h.getDiaSemana(),
                                    h.getHoraInicio().toString(),
                                    h.getHoraFin().toString(),
                                    h.getAula()));
                        }
                    }
                }
            }
        }
        filasFiltradas = new ArrayList<>(todasLasFilas);
    }

    /** Aplica filtros de búsqueda. */
    public void buscar() {
        String filtProf = buscarProfesor  != null ? buscarProfesor.trim().toLowerCase()  : "";
        String filtAsig = buscarAsignatura != null ? buscarAsignatura.trim().toLowerCase() : "";
        filasFiltradas = new ArrayList<>();
        for (FilaAsignacion f : todasLasFilas) {
            boolean matchProf = filtProf.isEmpty()
                    || f.getNombreProfesor().toLowerCase().contains(filtProf)
                    || f.getRfc().toLowerCase().contains(filtProf);
            boolean matchAsig = filtAsig.isEmpty()
                    || f.getAsignatura().toLowerCase().contains(filtAsig);
            if (matchProf && matchAsig) filasFiltradas.add(f);
        }
    }

    /** Limpia filtros y muestra todo. */
    public void limpiar() {
        buscarProfesor  = "";
        buscarAsignatura = "";
        filasFiltradas  = new ArrayList<>(todasLasFilas);
    }

    public List<FilaAsignacion> getFilasFiltradas() { return filasFiltradas; }
    public String getBuscarProfesor()               { return buscarProfesor; }
    public void   setBuscarProfesor(String v)       { this.buscarProfesor = v; }
    public String getBuscarAsignatura()             { return buscarAsignatura; }
    public void   setBuscarAsignatura(String v)     { this.buscarAsignatura = v; }
}
