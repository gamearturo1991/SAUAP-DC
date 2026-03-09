package mx.DC.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona", nullable = false)
    private Integer id;

    @NotNull @Size(max = 50)
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotNull @Size(max = 50)
    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String apellidoPaterno;

    @NotNull @Size(max = 50)
    @Column(name = "apellido_materno", nullable = false, length = 50)
    private String apellidoMaterno;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String v) { this.apellidoPaterno = v; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String v) { this.apellidoMaterno = v; }
    public String getNombreCompleto() { return nombre + " " + apellidoPaterno + " " + apellidoMaterno; }
}
