package mx.DC.facade;

import mx.DC.delegate.DelegateAsignacionDocente;
import mx.DC.entity.AsignacionDocente;

import java.util.List;

public class FacadeAsignacionDocente {

    private final DelegateAsignacionDocente delegateAsignacionDocente;

    public FacadeAsignacionDocente() {
        this.delegateAsignacionDocente = new DelegateAsignacionDocente();
    }

    public List<AsignacionDocente> obtenerTodos() {
        return delegateAsignacionDocente.obtenerTodos();
    }

    public AsignacionDocente obtenerPorId(Integer id) {
        return delegateAsignacionDocente.obtenerPorId(id);
    }

    public void guardar(AsignacionDocente asignacionDocente) {
        delegateAsignacionDocente.guardar(asignacionDocente);
    }

    public void actualizar(AsignacionDocente asignacionDocente) {
        delegateAsignacionDocente.actualizar(asignacionDocente);
    }

    public void eliminar(AsignacionDocente asignacionDocente) {
        delegateAsignacionDocente.eliminar(asignacionDocente);
    }
}

