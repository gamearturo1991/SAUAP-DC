package mx.DC.delegate;

import mx.DC.entity.Asignatura;
import mx.DC.integration.ServiceLocator;
import mx.DC.DAO.AsignaturaDAO;

import java.util.List;

public class DelegateAsignatura {

    public List<Asignatura> obtenerTodos() {
        AsignaturaDAO asignaturaDAO = ServiceLocator.getInstanceAsignaturaDAO();
        return asignaturaDAO.obtenerTodos();
    }

    public Asignatura obtenerPorId(Integer id) {
        AsignaturaDAO asignaturaDAO = ServiceLocator.getInstanceAsignaturaDAO();
        return asignaturaDAO.find(id).orElse(null);
    }

    public void guardar(Asignatura asignatura) {
        AsignaturaDAO asignaturaDAO = ServiceLocator.getInstanceAsignaturaDAO();
        asignaturaDAO.save(asignatura);
    }

    public void actualizar(Asignatura asignatura) {
        AsignaturaDAO asignaturaDAO = ServiceLocator.getInstanceAsignaturaDAO();
        asignaturaDAO.update(asignatura);
    }

    public void eliminar(Asignatura asignatura) {
        AsignaturaDAO asignaturaDAO = ServiceLocator.getInstanceAsignaturaDAO();
        asignaturaDAO.delete(asignatura);
    }
}

