package pe.edu.upeu.sysgestionturismo.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDestino;

    private String nombre;
    private String descripcion;
    private String ubicacion;
}
