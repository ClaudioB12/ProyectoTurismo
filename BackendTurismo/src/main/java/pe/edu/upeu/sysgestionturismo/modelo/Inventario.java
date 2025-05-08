package pe.edu.upeu.sysgestionturismo.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    private String nombreItem;
    private Integer cantidadDisponible;

    @ManyToOne
    @JoinColumn(name = "idDestino")
    private Destino destino;
}
