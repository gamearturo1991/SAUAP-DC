package mx.DC.integration;

import mx.DC.facade.FacadeAsignacionDocente;
import mx.DC.facade.FacadeAsignatura;
import mx.DC.facade.FacadeGrupo;
import mx.DC.facade.FacadeHorario;
import mx.DC.facade.FacadeProfesor;
import mx.DC.facade.FacadeUsuario;

public class ServiceFacadeLocator {

    private static FacadeUsuario facadeUsuario;
    private static FacadeProfesor facadeProfesor;
    private static FacadeAsignatura facadeAsignatura;
    private static FacadeAsignacionDocente facadeAsignacionDocente;
    private static FacadeHorario facadeHorario;
    private static FacadeGrupo facadeGrupo;

    public static FacadeUsuario getInstanceFacadeUsuario() {
        if (facadeUsuario == null) {
            facadeUsuario = new FacadeUsuario();
        }
        return facadeUsuario;
    }

    public static FacadeProfesor getInstanceFacadeProfesor() {
        if (facadeProfesor == null) {
            facadeProfesor = new FacadeProfesor();
        }
        return facadeProfesor;
    }

    public static FacadeAsignatura getInstanceFacadeAsignatura() {
        if (facadeAsignatura == null) {
            facadeAsignatura = new FacadeAsignatura();
        }
        return facadeAsignatura;
    }

    public static FacadeAsignacionDocente getInstanceFacadeAsignacionDocente() {
        if (facadeAsignacionDocente == null) {
            facadeAsignacionDocente = new FacadeAsignacionDocente();
        }
        return facadeAsignacionDocente;
    }

    public static FacadeHorario getInstanceFacadeHorario() {
        if (facadeHorario == null) {
            facadeHorario = new FacadeHorario();
        }
        return facadeHorario;
    }

    public static FacadeGrupo getInstanceFacadeGrupo() {
        if (facadeGrupo == null) {
            facadeGrupo = new FacadeGrupo();
        }
        return facadeGrupo;
    }
}
