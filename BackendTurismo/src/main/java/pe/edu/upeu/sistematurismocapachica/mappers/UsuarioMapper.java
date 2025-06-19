package pe.edu.upeu.sistematurismocapachica.mappers;

import org.springframework.stereotype.Component;
import pe.edu.upeu.sistematurismocapachica.dtos.UsuarioDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;
import pe.edu.upeu.sistematurismocapachica.modelo.Rol;

import java.util.Arrays;

@Component
public class UsuarioMapper {

    // Convertir entidad a DTO
    public UsuarioDto toDto(Usuario usuario) {
        return UsuarioDto.builder()
                .idUsuario(usuario.getIdUsuario())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol().name()) // Enum -> String
                .build();
    }

    // Convertir desde UsuarioCrearDto (record) a entidad
    public Usuario toEntity(UsuarioDto.UsuarioCrearDto dto) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(dto.correo());
        usuario.setClave(dto.clave());
        usuario.setRol(validarRol(dto.rol())); // Validación robusta
        return usuario;
    }

    // Convertir desde UsuarioDto (opcional)
    public Usuario fromDto(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(validarRol(dto.getRol()));
        return usuario;
    }

    // ✅ Valida el rol recibido como texto y lo convierte a Enum
    private Rol validarRol(String rolStr) {
        if (rolStr == null) return Rol.USUARIO;
        try {
            return Rol.valueOf(rolStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Rol.USUARIO; // Rol por defecto si no coincide
        }
    }
}
