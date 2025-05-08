package pe.edu.upeu.sysgestionturismo.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class PaqueteTuristico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaquete;

    private String nombre;
    private String descripcion;
    private Double precioTotal;

    @ManyToMany
    @JoinTable(
            name = "paquete_actividad",
            joinColumns = @JoinColumn(name = "idPaquete"),
            inverseJoinColumns = @JoinColumn(name = "idActividad")
    )
    private List<Actividad> actividades;

    @ManyToOne
    @JoinColumn(name = "idDestino")
    private Destino destino;
}