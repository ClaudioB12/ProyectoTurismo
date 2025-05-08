package pe.edu.upeu.sysgestionturismo.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String correo;
    private String contraseña;
    private String rol; // ADMIN, CLIENTE, etc.
}