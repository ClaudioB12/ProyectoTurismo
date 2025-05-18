package pe.edu.upeu.sistematurismocapachica.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String correo;
    private String clave;
    private String rol; // ADMIN, CLIENTE, etc.
}