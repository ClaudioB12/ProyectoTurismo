package pe.edu.upeu.sistematurismocapachica.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Hospedaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHospedaje;

    private String nombre;
    private String descripcion;
    private Double precioPorNoche;
    private Double latitud;
    private Double longitud;
    @ManyToOne
    @JoinColumn(name = "idDestino")
    private Destino destino;
}
