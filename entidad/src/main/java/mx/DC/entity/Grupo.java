package mx.DC.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "codigo_grupo", nullable = false)
    private Integer codigoGrupo;

    @NotNull
    @Column(name = "numero_grupo", nullable = false)
    private Integer numeroGrupo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCodigoGrupo() { return codigoGrupo; }
    public void setCodigoGrupo(Integer v) { this.codigoGrupo = v; }
    public Integer getNumeroGrupo() { return numeroGrupo; }
    public void setNumeroGrupo(Integer v) { this.numeroGrupo = v; }
    public String getDescripcion() { return codigoGrupo + "-" + numeroGrupo; }
}
