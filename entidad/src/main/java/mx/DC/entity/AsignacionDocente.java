package mx.DC.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "asignacion_docente")
public class AsignacionDocente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_docente", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_profesor", nullable = false)
    private Profesor idProfesor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_asignatura", nullable = false)
    private Asignatura idAsignatura;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo idGrupo;

    @NotNull
    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;

    @NotNull @Size(max = 20)
    @Column(name = "estado_asignacion", nullable = false, length = 20)
    private String estadoAsignacion;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Profesor getIdProfesor() { return idProfesor; }
    public void setIdProfesor(Profesor v) { this.idProfesor = v; }
    public Asignatura getIdAsignatura() { return idAsignatura; }
    public void setIdAsignatura(Asignatura v) { this.idAsignatura = v; }
    public Grupo getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Grupo v) { this.idGrupo = v; }
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate v) { this.fechaAsignacion = v; }
    public String getEstadoAsignacion() { return estadoAsignacion; }
    public void setEstadoAsignacion(String v) { this.estadoAsignacion = v; }

    public int getTotalHorasSemanales() {
        if (idAsignatura == null) return 0;
        return idAsignatura.getNumHorasClase()
             + idAsignatura.getNumHorasTaller()
             + idAsignatura.getNumHorasLaboratorio();
    }
}
