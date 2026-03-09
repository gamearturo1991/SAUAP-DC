package mx.DC.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nombre_usuario", length = 100)
    private String nombreUsuario;

    @Column(name = "contrasena_hash", length = 255)
    private String contrasenaHash;

    @Column(name = "rol", length = 50)
    private String rol;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor idProfesor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Profesor getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Profesor idProfesor) {
        this.idProfesor = idProfesor;
    }

    public boolean esAdministrador() {
        return rol != null && "administrador".equalsIgnoreCase(rol.trim());
    }

    // Compatibilidad con codigo legado que usa getContrasena()
    public String getContrasena() {
        return contrasenaHash;
    }

    public void setContrasena(String contrasena) {
        this.contrasenaHash = contrasena;
    }
}
