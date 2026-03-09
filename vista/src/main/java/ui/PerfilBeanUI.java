package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mx.DC.entity.AsignacionDocente;
import mx.DC.entity.Horario;
import mx.DC.entity.Profesor;
import mx.DC.negocio.AsignacionService;
import mx.DC.persistence.JPAUtil;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("perfilUI")
@ViewScoped
public class PerfilBeanUI implements Serializable {

    // Horas a mostrar en la tabla (07:00 – 20:00)
    private static final List<String> HORAS_DISPLAY = new ArrayList<>();
    static {
        for (int h = 7; h <= 20; h++) {
            HORAS_DISPLAY.add(String.format("%02d:00", h));
        }
    }

    // Colores para distinguir materias
    private static final String[] COLORES = {
        "#d4edda", "#cce5ff", "#fff3cd", "#f8d7da", "#e2d9f3", "#fce5cd"
    };

    @Inject
    private LoginBeanUI loginUI;

    private List<AsignacionDocente> misAsignaciones = new ArrayList<>();
    private AsignacionService asignacionService;

    // clave: "DiaSemana_HH:00" → nombre de la materia
    private Map<String, String> horarioCelda = new HashMap<>();
    // clave: nombre materia → color de fondo
    private Map<String, String> colorMateria  = new HashMap<>();

    @PostConstruct
    public void init() {
        asignacionService = new AsignacionService(JPAUtil.getEntityManager());
        cargarDatos();
    }

    private void cargarDatos() {
        if (loginUI.getUsuarioActual() == null
                || loginUI.getUsuarioActual().getIdProfesor() == null) return;

        Profesor profesor = loginUI.getUsuarioActual().getIdProfesor();
        misAsignaciones = asignacionService.obtenerPorProfesor(profesor);

        // Construir mapa de celdas
        List<Horario> horarios = asignacionService.obtenerHorariosPorProfesor(profesor);
        for (Horario h : horarios) {
            String nombreMateria = h.getIdAsignacionDocente().getIdAsignatura().getNombre();
            String etiqueta = nombreMateria
                    + (h.getAula() != null && !h.getAula().isBlank() ? "\n" + h.getAula() : "");

            // Asignar color si no tiene uno aún
            if (!colorMateria.containsKey(nombreMateria)) {
                colorMateria.put(nombreMateria, COLORES[colorMateria.size() % COLORES.length]);
            }

            // Marcar cada slot de hora cubierto por este bloque
            for (int slot = 7; slot <= 20; slot++) {
                LocalTime slotInicio = LocalTime.of(slot, 0);
                LocalTime slotFin   = LocalTime.of(slot + 1, 0);
                if (h.getHoraInicio().isBefore(slotFin) && h.getHoraFin().isAfter(slotInicio)) {
                    String key = h.getDiaSemana() + "_" + String.format("%02d:00", slot);
                    horarioCelda.put(key, etiqueta);
                }
            }
        }
    }

    /** Texto de la celda para el día y hora dados (null si vacía). */
    public String getHorarioCeldaValor(String dia, String hora) {
        return horarioCelda.get(dia + "_" + hora);
    }

    /** Estilo CSS inline para la celda (fondo de color si hay clase). */
    public String getEstiloCelda(String dia, String hora) {
        String val = horarioCelda.get(dia + "_" + hora);
        if (val == null) return "";
        // Obtener el nombre de materia (antes del posible \n del aula)
        String nombre = val.contains("\n") ? val.substring(0, val.indexOf("\n")) : val;
        String color = colorMateria.getOrDefault(nombre, "#d4edda");
        return "background:" + color + ";font-size:11px;font-weight:bold;padding:2px 3px;white-space:pre-line;";
    }

    public List<String> getHorasDisplay() { return HORAS_DISPLAY; }

    public int getNumMaterias() { return misAsignaciones.size(); }

    public int getTotalHorasSemanales() {
        return misAsignaciones.stream().mapToInt(AsignacionDocente::getTotalHorasSemanales).sum();
    }

    public String getNombreProfesor() {
        if (loginUI.getUsuarioActual() != null && loginUI.getUsuarioActual().getIdProfesor() != null)
            return loginUI.getUsuarioActual().getIdProfesor().getNombreCompleto();
        return loginUI.getUsuarioActual() != null ? loginUI.getUsuarioActual().getNombreUsuario() : "";
    }

    public String getRol() {
        if (loginUI.getUsuarioActual() != null)
            return loginUI.getUsuarioActual().getRol().toUpperCase();
        return "";
    }

    public List<AsignacionDocente> getMisAsignaciones() { return misAsignaciones; }
}
