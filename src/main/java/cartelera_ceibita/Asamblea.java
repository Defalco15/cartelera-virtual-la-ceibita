package cartelera_ceibita;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asambleas")
public class Asamblea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String lugar;

    private LocalDateTime fechaAsamblea;

    private LocalDateTime fechaFin;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDateTime getFechaAsamblea() {
        return fechaAsamblea;
    }

    public void setFechaAsamblea(LocalDateTime fechaAsamblea) {
        this.fechaAsamblea = fechaAsamblea;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}