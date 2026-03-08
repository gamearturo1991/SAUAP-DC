package mx.DC;

import mx.DC.entity.Usuario;
import mx.DC.facade.FacadeUsuario;
import mx.DC.integration.ServiceFacadeLocator;

public class prueba {
    public static void main(String[] args) {

        FacadeUsuario facade = ServiceFacadeLocator.getInstanceFacadeUsuario();


        String nombreUsuario = "juan";
        String contraseña = "mega";

        Usuario usuario = facade.login(nombreUsuario, contraseña);

        if (usuario != null) {
            System.out.println("✅ Inicio de sesión exitoso");
            System.out.println("ID Usuario: " + usuario.getId());
            System.out.println("Nombre de usuario: " + usuario.getNombreUsuario());
            System.out.println("Rol: " + usuario.getRol());
            if (usuario.getIdProfesor() != null) {
                System.out.println("ID Profesor asociado: " + usuario.getIdProfesor().getId());
            } else {
                System.out.println("Usuario no asociado a ningún profesor.");
            }
        } else {
            System.out.println("❌ Error: Usuario o contraseña incorrectos");
        }
    }
}