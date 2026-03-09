package mx.DC.facade;

import mx.DC.delegate.DelegateProfesor;
import mx.DC.entity.Profesor;

import java.util.List;

public class FacadeProfesor {

    private final DelegateProfesor delegateProfesor;

    public FacadeProfesor() {
        this.delegateProfesor = new DelegateProfesor();
    }

    public List<Profesor> obtenerTodos() {
        return delegateProfesor.obtenerTodos();
    }

    public Profesor obtenerPorId(Integer id) {
        return delegateProfesor.obtenerPorId(id);
    }

    public void guardar(Profesor profesor) {
        delegateProfesor.guardar(profesor);
    }

    public void actualizar(Profesor profesor) {
        delegateProfesor.actualizar(profesor);
    }

    public void eliminar(Profesor profesor) {
        delegateProfesor.eliminar(profesor);
    }
}

