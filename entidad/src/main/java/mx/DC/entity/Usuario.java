package mx.DC.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @NotNull @Size(max = 50)
    @Column(name = "nombre_usuario", nullable = false, length = 50, unique = true)
    private String nombreUsuario;

    @NotNull @Size(max = 255)
    @Column(name = "contrasena_hash", nullable = false)
    private String contrasenaHash;

    @NotNull
    @Column(name = "rol", nullable = false, length = 20)
    private String rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profesor")
    private Profesor idProfesor;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String v) { this.nombreUsuario = v; }
    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String v) { this.contrasenaHash = v; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public Profesor getIdProfesor() { return idProfesor; }
    public void setIdProfesor(Profesor v) { this.idProfesor = v; }
    public boolean esAdministrador() { return "administrador".equals(rol); }
    public boolean esProfesor() { return "profesor".equals(rol); }
}
