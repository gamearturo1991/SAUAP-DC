package mx.DC.delegate;

import mx.DC.entity.AsignacionDocente;
import mx.DC.integration.ServiceLocator;
import mx.DC.DAO.AsignacionDocenteDAO;

import java.util.List;

public class DelegateAsignacionDocente {

    public List<AsignacionDocente> obtenerTodos() {
        AsignacionDocenteDAO asignacionDocenteDAO = ServiceLocator.getInstanceAsignacionDocenteDAO();
        return asignacionDocenteDAO.obtenerTodos();
    }

    public AsignacionDocente obtenerPorId(Integer id) {
        AsignacionDocenteDAO asignacionDocenteDAO = ServiceLocator.getInstanceAsignacionDocenteDAO();
        return asignacionDocenteDAO.find(id).orElse(null);
    }

    public void guardar(AsignacionDocente asignacionDocente) {
        AsignacionDocenteDAO asignacionDocenteDAO = ServiceLocator.getInstanceAsignacionDocenteDAO();
        asignacionDocenteDAO.save(asignacionDocente);
    }

    public void actualizar(AsignacionDocente asignacionDocente) {
        AsignacionDocenteDAO asignacionDocenteDAO = ServiceLocator.getInstanceAsignacionDocenteDAO();
        asignacionDocenteDAO.update(asignacionDocente);
    }

    public void eliminar(AsignacionDocente asignacionDocente) {
        AsignacionDocenteDAO asignacionDocenteDAO = ServiceLocator.getInstanceAsignacionDocenteDAO();
        asignacionDocenteDAO.delete(asignacionDocente);
    }
}

