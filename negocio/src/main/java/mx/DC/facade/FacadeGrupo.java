package mx.DC.facade;

import mx.DC.delegate.DelegateGrupo;
import mx.DC.entity.Grupo;

import java.util.List;

public class FacadeGrupo {

    private final DelegateGrupo delegateGrupo;

    public FacadeGrupo() {
        this.delegateGrupo = new DelegateGrupo();
    }

    public List<Grupo> obtenerTodos() {
        return delegateGrupo.obtenerTodos();
    }

    public Grupo obtenerPorId(Integer id) {
        return delegateGrupo.obtenerPorId(id);
    }
}

