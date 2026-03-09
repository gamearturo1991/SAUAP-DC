package ui;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import mx.DC.entity.Persona;
import mx.DC.entity.Profesor;
import mx.DC.facade.FacadeProfesor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("profesoresUI")
@SessionScoped
public class ProfesoresUI implements Serializable {

    private final FacadeProfesor facadeProfesor;
    private List<Profesor> profesores;
    private String filtro;

    public ProfesoresUI() {
        this.facadeProfesor = new FacadeProfesor();
    }

    @PostConstruct
    public void init() {
        cargarProfesores();
    }

    public void cargarProfesores() {
        profesores = facadeProfesor.obtenerTodos();
        if (profesores == null) {
            profesores = new ArrayList<>();
        }
    }

    public List<Profesor> getProfesoresFiltrados() {
        if (filtro == null || filtro.trim().isEmpty()) {
            return profesores;
        }

        String criterio = filtro.trim().toLowerCase();
        List<Profesor> resultado = new ArrayList<>();
        for (Profesor profesor : profesores) {
            String nombreCompleto = obtenerNombreCompleto(profesor).toLowerCase();
            String rfc = profesor.getRfc() != null ? profesor.getRfc().toLowerCase() : "";
            if (nombreCompleto.contains(criterio) || rfc.contains(criterio)) {
                resultado.add(profesor);
            }
        }
        return resultado;
    }

    public String obtenerNombreCompleto(Profesor profesor) {
        if (profesor == null) {
            return "Sin profesor";
        }
        Persona persona = profesor.getIdPersona();
        if (persona == null) {
            return "Sin datos";
        }

        String nombre = persona.getNombre() != null ? persona.getNombre() : "";
        String apPaterno = persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "";
        String apMaterno = persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : "";
        return (nombre + " " + apPaterno + " " + apMaterno).trim();
    }

    public int getTotalProfesores() {
        return profesores != null ? profesores.size() : 0;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
}

