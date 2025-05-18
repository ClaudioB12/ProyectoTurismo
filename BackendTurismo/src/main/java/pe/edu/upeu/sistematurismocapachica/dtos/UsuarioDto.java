package pe.edu.upeu.sistematurismocapachica.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
    
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDto {
    private Long idUsuario;
    private String correo;
    private String clave;
    private String rol;
    private String token;
    
    public record CredencialesDto(String correo, String clave) { }

    public record UsuarioCrearDto(String correo, String clave, String rol) { }
}
