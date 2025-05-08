package pe.edu.upeu.sysgestionturismo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
    
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDto {
    private Long idUsuario;
    private String correo;
    private String contraseña;
    private String rol;
    private String token;
    
    public record CredencialesDto(String correo, String contraseña) { }

    public record UsuarioCrearDto(String correo, String contraseña, String rol) { }
}
