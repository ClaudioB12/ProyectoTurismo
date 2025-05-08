package pe.edu.upeu.sistematurismocapachica.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActividad;

    private String nombre;
    private String descripcion;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "idDestino")
    private Destino destino;
}