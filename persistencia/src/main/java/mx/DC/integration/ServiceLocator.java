/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.DC.integration;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.*;
import mx.DC.persistence.HibernateUtil;

public class ServiceLocator {

    private static ProfesorDAO profesorDAO;
    private static UsuarioDAO usuarioDAO;
    private static AsignaturaDAO asignaturaDAO;
    private static AsignacionDocenteDAO asignacionDocenteDAO;
    private static HorarioDAO horarioDAO;
    private static GrupoDAO grupoDAO;

    private static EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    public static ProfesorDAO getInstanceProfesorDAO() {
        if (profesorDAO == null) {
            profesorDAO = new ProfesorDAO(getEntityManager());
        }
        return profesorDAO;
    }

    public static UsuarioDAO getInstanceUsuarioDAO() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO(getEntityManager());
        }
        return usuarioDAO;
    }

    public static AsignaturaDAO getInstanceAsignaturaDAO() {
        if (asignaturaDAO == null) {
            asignaturaDAO = new AsignaturaDAO(getEntityManager());
        }
        return asignaturaDAO;
    }

    public static AsignacionDocenteDAO getInstanceAsignacionDocenteDAO() {
        if (asignacionDocenteDAO == null) {
            asignacionDocenteDAO = new AsignacionDocenteDAO(getEntityManager());
        }
        return asignacionDocenteDAO;
    }

    public static HorarioDAO getInstanceHorarioDAO() {
        if (horarioDAO == null) {
            horarioDAO = new HorarioDAO(getEntityManager());
        }
        return horarioDAO;
    }

    public static GrupoDAO getInstanceGrupoDAO() {
        if (grupoDAO == null) {
            grupoDAO = new GrupoDAO(getEntityManager());
        }
        return grupoDAO;
    }
}
