package mx.DC.delegate;

import mx.DC.entity.Horario;
import mx.DC.integration.ServiceLocator;
import mx.DC.DAO.HorarioDAO;

import java.util.List;

public class DelegateHorario {

    public List<Horario> obtenerTodos() {
        HorarioDAO horarioDAO = ServiceLocator.getInstanceHorarioDAO();
        return horarioDAO.obtenerTodos();
    }

    public Horario obtenerPorId(Integer id) {
        HorarioDAO horarioDAO = ServiceLocator.getInstanceHorarioDAO();
        return horarioDAO.find(id).orElse(null);
    }

    public void guardar(Horario horario) {
        HorarioDAO horarioDAO = ServiceLocator.getInstanceHorarioDAO();
        horarioDAO.save(horario);
    }

    public void actualizar(Horario horario) {
        HorarioDAO horarioDAO = ServiceLocator.getInstanceHorarioDAO();
        horarioDAO.update(horario);
    }

    public void eliminar(Horario horario) {
        HorarioDAO horarioDAO = ServiceLocator.getInstanceHorarioDAO();
        horarioDAO.delete(horario);
    }
}

