package mx.DC.negocio;

import jakarta.persistence.EntityManager;
import mx.DC.DAO.PersonaDAO;
import mx.DC.DAO.ProfesorDAO;
import mx.DC.DAO.UsuarioDAO;
import mx.DC.entity.Persona;
import mx.DC.entity.Profesor;
import mx.DC.entity.Usuario;

import java.util.List;

public class ProfesorService {

    private final ProfesorDAO profesorDAO;
    private final PersonaDAO personaDAO;
    private final UsuarioDAO usuarioDAO;

    public ProfesorService(EntityManager em) {
        profesorDAO = new ProfesorDAO(em);
        personaDAO = new PersonaDAO(em);
        usuarioDAO = new UsuarioDAO(em);
    }

    public Profesor registrarDocente(String rfc, String nombre, String apellidoPaterno, String apellidoMaterno, String tipoContrato) {
        if (rfc == null || rfc.isBlank()) throw new IllegalArgumentException("RFC requerido");
        if (rfc.trim().length() > 13) throw new IllegalArgumentException("RFC maximo 13 caracteres");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido");
        if (apellidoPaterno == null || apellidoPaterno.isBlank()) throw new IllegalArgumentException("Apellido paterno requerido");
        if (apellidoMaterno == null || apellidoMaterno.isBlank()) throw new IllegalArgumentException("Apellido materno requerido");
        if (profesorDAO.findByRFC(rfc.trim()) != null)
            throw new IllegalStateException("Ya existe un docente con RFC: " + rfc);

        Persona persona = new Persona();
        persona.setNombre(nombre.trim());
        persona.setApellidoPaterno(apellidoPaterno.trim());
        persona.setApellidoMaterno(apellidoMaterno.trim());
        personaDAO.save(persona);

        Profesor profesor = new Profesor();
        profesor.setRfc(rfc.trim());
        profesor.setIdPersona(persona);
        profesor.setTipoContrato(tipoContrato != null && !tipoContrato.isBlank() ? tipoContrato : "planta");
        profesorDAO.save(profesor);
        return profesor;
    }

    public Profesor registrarDocenteConUsuario(
            String rfc,
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String tipoContrato,
            String nombreUsuario,
            String contrasena,
            String confirmacionContrasena
    ) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            throw new IllegalArgumentException("Nombre de usuario requerido");
        }
        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("Contrasena requerida");
        }
        if (contrasena.length() < 6) {
            throw new IllegalArgumentException("La contrasena debe tener al menos 6 caracteres");
        }
        if (confirmacionContrasena == null || !contrasena.equals(confirmacionContrasena)) {
            throw new IllegalArgumentException("Las contrasenas no coinciden");
        }
        if (usuarioDAO.findByNombreUsuario(nombreUsuario.trim()) != null) {
            throw new IllegalStateException("El nombre de usuario ya existe");
        }

        Profesor profesor = registrarDocente(rfc, nombre, apellidoPaterno, apellidoMaterno, tipoContrato);

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario.trim());
        usuario.setContrasenaHash(UsuarioService.md5(contrasena.trim()));
        usuario.setRol("profesor");
        usuario.setIdProfesor(profesor);
        usuarioDAO.save(usuario);

        return profesor;
    }

    public List<Profesor> obtenerTodos() { return profesorDAO.obtenerTodos(); }
    public Profesor buscarPorId(int id) { return profesorDAO.find(id).orElse(null); }
    public Profesor buscarPorRFC(String rfc) { return profesorDAO.findByRFC(rfc); }
}
