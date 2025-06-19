package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.dtos.UsuarioDto;
import pe.edu.upeu.sistematurismocapachica.excepciones.ModelNotFoundException;
import pe.edu.upeu.sistematurismocapachica.mappers.UsuarioMapper;
import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;
import pe.edu.upeu.sistematurismocapachica.repositorio.UsuarioRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IUsuarioService;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioDto login(UsuarioDto.CredencialesDto credentialsDto) {
        Usuario user = usuarioRepository.findByCorreo(credentialsDto.correo())
                .orElseThrow(() -> new ModelNotFoundException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.clave()), user.getClave())) {
            return usuarioMapper.toDto(user);
        }

        throw new ModelNotFoundException("Contraseña incorrecta", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UsuarioDto register(UsuarioDto.UsuarioCrearDto userDto) {
        Optional<Usuario> optionalUser = usuarioRepository.findByCorreo(userDto.correo());
        if (optionalUser.isPresent()) {
            throw new ModelNotFoundException("Correo ya registrado", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioMapper.toEntity(userDto); // ✅ Aquí estaba el error
        usuario.setClave(passwordEncoder.encode(CharBuffer.wrap(userDto.clave())));
        Usuario savedUser = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(savedUser);
    }

}
