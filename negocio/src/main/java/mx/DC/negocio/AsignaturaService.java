package mx.DC.negocio;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.AsignaturaDAO;
import mx.DC.entity.Asignatura;

import java.util.List;

public class AsignaturaService {

    private final AsignaturaDAO asignaturaDAO;
    private final EntityManager entityManager;

    public AsignaturaService(EntityManager em) {
        this.entityManager = em;
        this.asignaturaDAO = new AsignaturaDAO(em);
    }

    /**
     * Registra una nueva asignatura en el sistema.
     *
     * @throws IllegalArgumentException si algún campo es inválido
     * @throws IllegalStateException    si ya existe una asignatura con el mismo nombre
     */
    public Asignatura registrarAsignatura(String nombre, int horasClase, int horasTaller, int horasLaboratorio) {
        validarCampos(nombre, horasClase, horasTaller, horasLaboratorio);

        if (asignaturaDAO.findByNombre(nombre.trim()) != null) {
            throw new IllegalStateException("Ya existe una asignatura con el nombre: " + nombre);
        }

        Asignatura asignatura = new Asignatura();
        asignatura.setNombre(nombre.trim());
        asignatura.setNumHorasClase(horasClase);
        asignatura.setNumHorasTaller(horasTaller);
        asignatura.setNumHorasLaboratorio(horasLaboratorio);

        entityManager.getTransaction().begin();
        asignaturaDAO.create(asignatura);
        entityManager.getTransaction().commit();

        return asignatura;
    }

    /** Retorna todas las asignaturas registradas. */
    public List<Asignatura> obtenerTodas() {
        return asignaturaDAO.obtenerTodos();
    }

    /** Busca una asignatura por su ID. Retorna null si no existe. */
    public Asignatura buscarPorId(int id) {
        return asignaturaDAO.find(id);
    }

    /** Busca asignaturas cuyo nombre contenga el texto indicado (búsqueda parcial). */
    public List<Asignatura> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de búsqueda no puede estar vacío");
        }
        return asignaturaDAO.findByNombreLike(nombre.trim());
    }

    /**
     * Actualiza los datos de una asignatura existente.
     *
     * @throws IllegalArgumentException si algún campo es inválido
     * @throws IllegalStateException    si la asignatura no existe o el nuevo nombre ya está en uso
     */
    public Asignatura actualizarAsignatura(int id, String nombre, int horasClase, int horasTaller, int horasLaboratorio) {
        validarCampos(nombre, horasClase, horasTaller, horasLaboratorio);

        Asignatura existente = asignaturaDAO.find(id);
        if (existente == null) {
            throw new IllegalStateException("No existe una asignatura con id: " + id);
        }

        Asignatura porNombre = asignaturaDAO.findByNombre(nombre.trim());
        if (porNombre != null && !porNombre.getId().equals(id)) {
            throw new IllegalStateException("Ya existe otra asignatura con el nombre: " + nombre);
        }

        existente.setNombre(nombre.trim());
        existente.setNumHorasClase(horasClase);
        existente.setNumHorasTaller(horasTaller);
        existente.setNumHorasLaboratorio(horasLaboratorio);

        entityManager.getTransaction().begin();
        Asignatura actualizada = asignaturaDAO.edit(existente);
        entityManager.getTransaction().commit();

        return actualizada;
    }

    /**
     * Elimina una asignatura por su ID.
     *
     * @throws IllegalStateException si la asignatura no existe
     */
    public void eliminarAsignatura(int id) {
        Asignatura asignatura = asignaturaDAO.find(id);
        if (asignatura == null) {
            throw new IllegalStateException("No existe una asignatura con id: " + id);
        }

        entityManager.getTransaction().begin();
        asignaturaDAO.destroy(asignatura);
        entityManager.getTransaction().commit();
    }

    private void validarCampos(String nombre, int horasClase, int horasTaller, int horasLaboratorio) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la asignatura no puede estar vacío");
        }
        if (nombre.trim().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar 100 caracteres");
        }
        if (horasClase < 0) {
            throw new IllegalArgumentException("Las horas de clase no pueden ser negativas");
        }
        if (horasTaller < 0) {
            throw new IllegalArgumentException("Las horas de taller no pueden ser negativas");
        }
        if (horasLaboratorio < 0) {
            throw new IllegalArgumentException("Las horas de laboratorio no pueden ser negativas");
        }
    }
}
