/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.DC.integration;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.AlumnoDAO;
import mx.DC.DAO.AsignacionDocenteDAO;
import mx.DC.DAO.AsignaturaDAO;
import mx.DC.DAO.HorarioDAO;
import mx.DC.DAO.ProfesorDAO;
import mx.DC.DAO.UsuarioDAO;
import mx.DC.DAO.GrupoDAO;
import mx.DC.persistence.HibernateUtil;


/**
 *
 * @author total
 */
public class ServiceLocator {

    private static AlumnoDAO alumnoDAO;
    private static UsuarioDAO usuarioDAO;
    private static ProfesorDAO profesorDAO;
    private static AsignaturaDAO asignaturaDAO;
    private static AsignacionDocenteDAO asignacionDocenteDAO;
    private static HorarioDAO horarioDAO;
    private static GrupoDAO grupoDAO;

    private static EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }

    /**
     * se crea la instancia para alumno DAO si esta no existe
     */
    public static AlumnoDAO getInstanceAlumnoDAO(){
        if(alumnoDAO == null){
            alumnoDAO = new AlumnoDAO(getEntityManager());
            return alumnoDAO;
        } else{
            return alumnoDAO;
        }
    }
    /**
     * se crea la instancia de usuarioDAO si esta no existe
     */
    public static UsuarioDAO getInstanceUsuarioDAO(){
        if(usuarioDAO == null){
            usuarioDAO = new UsuarioDAO(getEntityManager());
            return usuarioDAO;
        } else{
            return usuarioDAO;
        }
    }

    /**
     * se crea la instancia para ProfesorDAO si esta no existe
     */
    public static ProfesorDAO getInstanceProfesorDAO(){
        if(profesorDAO == null){
            profesorDAO = new ProfesorDAO(getEntityManager());
            return profesorDAO;
        } else{
            return profesorDAO;
        }
    }

    /**
     * se crea la instancia para AsignaturaDAO si esta no existe
     */
    public static AsignaturaDAO getInstanceAsignaturaDAO(){
        if(asignaturaDAO == null){
            asignaturaDAO = new AsignaturaDAO(getEntityManager());
            return asignaturaDAO;
        } else{
            return asignaturaDAO;
        }
    }

    /**
     * se crea la instancia para AsignacionDocenteDAO si esta no existe
     */
    public static AsignacionDocenteDAO getInstanceAsignacionDocenteDAO(){
        if(asignacionDocenteDAO == null){
            asignacionDocenteDAO = new AsignacionDocenteDAO(getEntityManager());
            return asignacionDocenteDAO;
        } else{
            return asignacionDocenteDAO;
        }
    }

    /**
     * se crea la instancia para HorarioDAO si esta no existe
     */
    public static HorarioDAO getInstanceHorarioDAO(){
        if(horarioDAO == null){
            horarioDAO = new HorarioDAO(getEntityManager());
            return horarioDAO;
        } else{
            return horarioDAO;
        }
    }

    /**
     * se crea la instancia para GrupoDAO si esta no existe
     */
    public static GrupoDAO getInstanceGrupoDAO(){
        if(grupoDAO == null){
            grupoDAO = new GrupoDAO(getEntityManager());
            return grupoDAO;
        } else{
            return grupoDAO;
        }
    }

}
