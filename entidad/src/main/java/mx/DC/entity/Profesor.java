package mx.DC.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "profesor")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona idPersona;

    @NotNull @Size(max = 13)
    @Column(name = "RFC", nullable = false, length = 13)
    private String rfc;

    @NotNull @Size(max = 20)
    @Column(name = "tipo_contrato", nullable = false, length = 20)
    private String tipoContrato = "planta";

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Persona getIdPersona() { return idPersona; }
    public void setIdPersona(Persona v) { this.idPersona = v; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String v) { this.tipoContrato = v; }
    public String getNombreCompleto() { return idPersona != null ? idPersona.getNombreCompleto() : rfc; }
}
