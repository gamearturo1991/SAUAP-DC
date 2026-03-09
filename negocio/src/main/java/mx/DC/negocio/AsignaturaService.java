package mx.DC.negocio;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.AsignaturaDAO;
import mx.DC.entity.Asignatura;

import java.util.List;

public class AsignaturaService {

    private final AsignaturaDAO asignaturaDAO;

    public AsignaturaService(EntityManager em) {
        this.asignaturaDAO = new AsignaturaDAO(em);
    }

    public Asignatura registrarAsignatura(String nombre, int horasClase, int horasTaller, int horasLaboratorio) {
        validarCampos(nombre, horasClase, horasTaller, horasLaboratorio);
        if (asignaturaDAO.findByOneParameterUnique(nombre.trim(), "nombre") != null)
            throw new IllegalStateException("Ya existe una asignatura con el nombre: " + nombre);
        Asignatura a = new Asignatura();
        a.setNombre(nombre.trim());
        a.setNumHorasClase(horasClase);
        a.setNumHorasTaller(horasTaller);
        a.setNumHorasLaboratorio(horasLaboratorio);
        asignaturaDAO.save(a);
        return a;
    }

    public List<Asignatura> obtenerTodas() { return asignaturaDAO.findAll(); }

    public Asignatura buscarPorId(int id) { return asignaturaDAO.find(id).orElse(null); }

    public Asignatura actualizarAsignatura(int id, String nombre, int horasClase, int horasTaller, int horasLaboratorio) {
        validarCampos(nombre, horasClase, horasTaller, horasLaboratorio);
        Asignatura existente = asignaturaDAO.find(id)
                .orElseThrow(() -> new IllegalStateException("No existe asignatura con id: " + id));
        Asignatura porNombre = asignaturaDAO.findByOneParameterUnique(nombre.trim(), "nombre");
        if (porNombre != null && !porNombre.getId().equals(id))
            throw new IllegalStateException("Ya existe otra asignatura con el nombre: " + nombre);
        existente.setNombre(nombre.trim());
        existente.setNumHorasClase(horasClase);
        existente.setNumHorasTaller(horasTaller);
        existente.setNumHorasLaboratorio(horasLaboratorio);
        asignaturaDAO.update(existente);
        return existente;
    }

    public void eliminarAsignatura(int id) {
        asignaturaDAO.delete(
            asignaturaDAO.find(id).orElseThrow(() -> new IllegalStateException("No existe asignatura con id: " + id))
        );
    }

    private void validarCampos(String nombre, int hc, int ht, int hl) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre no puede estar vacio");
        if (nombre.trim().length() > 100) throw new IllegalArgumentException("El nombre no puede superar 100 caracteres");
        if (hc < 0) throw new IllegalArgumentException("Horas de clase no pueden ser negativas");
        if (ht < 0) throw new IllegalArgumentException("Horas de taller no pueden ser negativas");
        if (hl < 0) throw new IllegalArgumentException("Horas de laboratorio no pueden ser negativas");
    }
}
