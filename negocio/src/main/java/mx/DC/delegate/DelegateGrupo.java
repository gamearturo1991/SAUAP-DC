package mx.DC.delegate;

import mx.DC.DAO.GrupoDAO;
import mx.DC.entity.Grupo;
import mx.DC.integration.ServiceLocator;

import java.util.List;

public class DelegateGrupo {

    public List<Grupo> obtenerTodos() {
        GrupoDAO grupoDAO = ServiceLocator.getInstanceGrupoDAO();
        return grupoDAO.findAll();
    }

    public Grupo obtenerPorId(Integer id) {
        GrupoDAO grupoDAO = ServiceLocator.getInstanceGrupoDAO();
        return grupoDAO.find(id).orElse(null);
    }
}

