package pe.edu.upeu.sistematurismocapachica.dtos;

import lombok.Data;

@Data
public class ClienteDto {
    private Long idCliente;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String direccion;

    // Nueva propiedad para la foto (ruta o URL)
    private String fotoPerfilUrl;
}

