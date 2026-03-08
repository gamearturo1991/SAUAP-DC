package mx.DC.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "asignatura")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignatura", nullable = false)
    private Integer id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Column(name = "num_horas_clase", nullable = false)
    private Integer numHorasClase;

    @NotNull
    @Column(name = "num_horas_taller", nullable = false)
    private Integer numHorasTaller;

    @NotNull
    @Column(name = "num_horas_laboratorio", nullable = false)
    private Integer numHorasLaboratorio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumHorasClase() {
        return numHorasClase;
    }

    public void setNumHorasClase(Integer numHorasClase) {
        this.numHorasClase = numHorasClase;
    }

    public Integer getNumHorasTaller() {
        return numHorasTaller;
    }

    public void setNumHorasTaller(Integer numHorasTaller) {
        this.numHorasTaller = numHorasTaller;
    }

    public Integer getNumHorasLaboratorio() {
        return numHorasLaboratorio;
    }

    public void setNumHorasLaboratorio(Integer numHorasLaboratorio) {
        this.numHorasLaboratorio = numHorasLaboratorio;
    }
}
