package mx.DC.facade;

import mx.DC.delegate.DelegateHorario;
import mx.DC.entity.Horario;

import java.util.List;

public class FacadeHorario {

    private final DelegateHorario delegateHorario;

    public FacadeHorario() {
        this.delegateHorario = new DelegateHorario();
    }

    public List<Horario> obtenerTodos() {
        return delegateHorario.obtenerTodos();
    }

    public Horario obtenerPorId(Integer id) {
        return delegateHorario.obtenerPorId(id);
    }

    public void guardar(Horario horario) {
        delegateHorario.guardar(horario);
    }

    public void actualizar(Horario horario) {
        delegateHorario.actualizar(horario);
    }

    public void eliminar(Horario horario) {
        delegateHorario.eliminar(horario);
    }
}

