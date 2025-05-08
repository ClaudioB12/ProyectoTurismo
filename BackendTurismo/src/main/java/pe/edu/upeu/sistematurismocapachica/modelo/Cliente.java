package pe.edu.upeu.sistematurismocapachica.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String direccion;
}
