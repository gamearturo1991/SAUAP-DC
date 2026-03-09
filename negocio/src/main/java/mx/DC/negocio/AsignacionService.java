package mx.DC.negocio;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.AsignacionDocenteDAO;
import mx.DC.DAO.AsignaturaDAO;
import mx.DC.DAO.GrupoDAO;
import mx.DC.DAO.HorarioDAO;
import mx.DC.DAO.ProfesorDAO;
import mx.DC.entity.AsignacionDocente;
import mx.DC.entity.Asignatura;
import mx.DC.entity.Grupo;
import mx.DC.entity.Horario;
import mx.DC.entity.Profesor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AsignacionService {

    private final AsignacionDocenteDAO asignacionDAO;
    private final ProfesorDAO profesorDAO;
    private final AsignaturaDAO asignaturaDAO;
    private final GrupoDAO grupoDAO;
    private final HorarioDAO horarioDAO;

    public AsignacionService(EntityManager em) {
        asignacionDAO = new AsignacionDocenteDAO(em);
        profesorDAO   = new ProfesorDAO(em);
        asignaturaDAO = new AsignaturaDAO(em);
        grupoDAO      = new GrupoDAO(em);
        horarioDAO    = new HorarioDAO(em);
    }

    /**
     * Crea una AsignacionDocente junto con su bloque de Horario.
     * Valida que no exista traslape de horario para el mismo profesor en el mismo día.
     *
     * @param idProfesor   ID del profesor
     * @param idAsignatura ID de la asignatura
     * @param idGrupo      ID del grupo
     * @param tipoContrato tipo de contrato (planta, interino, tiempo_completo)
     * @param diaSemana    día de la semana (Lunes, Martes, …, Sábado)
     * @param horaInicioStr hora de inicio en formato HH:mm
     * @param horaFinStr    hora de fin en formato HH:mm
     * @param aula          aula (puede ser null)
     */
    public AsignacionDocente asignar(int idProfesor, int idAsignatura, int idGrupo,
                                     String diaSemana,
                                     String horaInicioStr, String horaFinStr, String aula) {

        // --- Validaciones de campos ---
        if (diaSemana == null || diaSemana.isBlank())
            throw new IllegalArgumentException("Debe seleccionar un día de la semana");
        if (horaInicioStr == null || horaInicioStr.isBlank())
            throw new IllegalArgumentException("Hora de inicio requerida");
        if (horaFinStr == null || horaFinStr.isBlank())
            throw new IllegalArgumentException("Hora de fin requerida");

        LocalTime horaInicio;
        LocalTime horaFin;
        try {
            horaInicio = LocalTime.parse(horaInicioStr);
            horaFin    = LocalTime.parse(horaFinStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm");
        }

        if (!horaFin.isAfter(horaInicio))
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");

        // --- Cargar entidades ---
        Profesor prof = profesorDAO.find(idProfesor)
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"));
        Asignatura asig = asignaturaDAO.find(idAsignatura)
                .orElseThrow(() -> new IllegalArgumentException("Asignatura no encontrada"));
        Grupo grp = grupoDAO.find(idGrupo)
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));

        // --- Validar traslape ---
        List<Horario> traslapes = horarioDAO.findTraslapeProfesor(
                idProfesor, diaSemana, horaInicio, horaFin);
        if (!traslapes.isEmpty()) {
            Horario h = traslapes.get(0);
            throw new IllegalStateException(
                    "Traslape de horario: el profesor ya tiene clase el " + diaSemana
                    + " de " + h.getHoraInicio() + " a " + h.getHoraFin()
                    + " (" + h.getIdAsignacionDocente().getIdAsignatura().getNombre() + ")");
        }

        // --- Crear AsignacionDocente ---
        AsignacionDocente ad = new AsignacionDocente();
        ad.setIdProfesor(prof);
        ad.setIdAsignatura(asig);
        ad.setIdGrupo(grp);
        ad.setFechaAsignacion(LocalDate.now());
        ad.setEstadoAsignacion("activo");
        asignacionDAO.save(ad);

        // --- Crear Horario asociado ---
        Horario horario = new Horario();
        horario.setIdAsignacionDocente(ad);
        horario.setDiaSemana(diaSemana);
        horario.setHoraInicio(horaInicio);
        horario.setHoraFin(horaFin);
        horario.setAula(aula != null && !aula.isBlank() ? aula.trim() : null);
        horarioDAO.save(horario);

        return ad;
    }

    public List<AsignacionDocente> obtenerPorProfesor(Profesor profesor) {
        return asignacionDAO.findByProfesor(profesor);
    }

    public List<Horario> obtenerHorariosPorProfesor(Profesor profesor) {
        List<AsignacionDocente> asignaciones = asignacionDAO.findByProfesor(profesor);
        List<Horario> todos = new ArrayList<>();
        for (AsignacionDocente ad : asignaciones) {
            todos.addAll(horarioDAO.findByAsignacion(ad));
        }
        return todos;
    }

    public List<Horario> obtenerHorariosPorAsignacion(AsignacionDocente ad) {
        return horarioDAO.findByAsignacion(ad);
    }

    public List<AsignacionDocente> obtenerTodas() { return asignacionDAO.findAll(); }
}
