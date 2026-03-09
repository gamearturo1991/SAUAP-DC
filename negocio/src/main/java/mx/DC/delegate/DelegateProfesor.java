package mx.DC.delegate;

import mx.DC.entity.Profesor;
import mx.DC.integration.ServiceLocator;
import mx.DC.DAO.ProfesorDAO;

import java.util.List;

public class DelegateProfesor {

    public List<Profesor> obtenerTodos() {
        ProfesorDAO profesorDAO = ServiceLocator.getInstanceProfesorDAO();
        return profesorDAO.obtenerTodos();
    }

    public Profesor obtenerPorId(Integer id) {
        ProfesorDAO profesorDAO = ServiceLocator.getInstanceProfesorDAO();
        return profesorDAO.find(id).orElse(null);
    }

    public void guardar(Profesor profesor) {
        ProfesorDAO profesorDAO = ServiceLocator.getInstanceProfesorDAO();
        profesorDAO.save(profesor);
    }

    public void actualizar(Profesor profesor) {
        ProfesorDAO profesorDAO = ServiceLocator.getInstanceProfesorDAO();
        profesorDAO.update(profesor);
    }

    public void eliminar(Profesor profesor) {
        ProfesorDAO profesorDAO = ServiceLocator.getInstanceProfesorDAO();
        profesorDAO.delete(profesor);
    }
}

