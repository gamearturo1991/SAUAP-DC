package mx.DC.facade;

import mx.DC.delegate.DelegateAsignatura;
import mx.DC.entity.Asignatura;

import java.util.List;

public class FacadeAsignatura {

    private final DelegateAsignatura delegateAsignatura;

    public FacadeAsignatura() {
        this.delegateAsignatura = new DelegateAsignatura();
    }

    public List<Asignatura> obtenerTodos() {
        return delegateAsignatura.obtenerTodos();
    }

    public Asignatura obtenerPorId(Integer id) {
        return delegateAsignatura.obtenerPorId(id);
    }

    public void guardar(Asignatura asignatura) {
        delegateAsignatura.guardar(asignatura);
    }

    public void actualizar(Asignatura asignatura) {
        delegateAsignatura.actualizar(asignatura);
    }

    public void eliminar(Asignatura asignatura) {
        delegateAsignatura.eliminar(asignatura);
    }
}

