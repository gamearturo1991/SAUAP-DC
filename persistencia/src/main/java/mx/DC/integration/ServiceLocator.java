/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.DC.integration;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.ProfesorDAO;
import mx.DC.DAO.UsuarioDAO;
import mx.DC.entity.Profesor;
import mx.DC.persistence.HibernateUtil;


/**
 *
 * @author total
 */
public class ServiceLocator {

    private static ProfesorDAO profesorDAO;
    private static UsuarioDAO usuarioDAO;

    private static EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }

    /**
     * se crea la instancia para alumno DAO si esta no existe
     */
    public static ProfesorDAO getInstanceAlumnoDAO(){
        if(profesorDAO == null){
           profesorDAO = new ProfesorDAO(getEntityManager());
            return profesorDAO;
        } else{
            return profesorDAO;
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
    
}
