package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;
import pe.edu.upeu.sistematurismocapachica.repositorio.UsuarioRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IUsuarioService;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
/*@Override
    public UsuarioDto login(UsuarioDto.CredencialesDto credentialsDto) {
        Usuario user = usuarioRepository.findOneByUser(credentialsDto.correo())
                .orElseThrow(() -> new ModelNotFoundException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.clave()), user.getClave())) {
            return userMapper.toDTO(user);
        }

        throw new ModelNotFoundException("Invalid password", HttpStatus.BAD_REQUEST);
    }
*/


   /* @Override
    public UsuarioDTO register(UsuarioDTO.UsuarioCrearDto userDto) {
        Optional<Usuario> optionalUser = repo.findOneByUser(userDto.user());
        if (optionalUser.isPresent()) {
            throw new ModelNotFoundException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        Usuario user = userMapper.toEntityFromCADTO(userDto);
        user.setClave(passwordEncoder.encode(CharBuffer.wrap(userDto.clave())));
        Usuario savedUser = repo.save(user);
        Rol r;
        switch (userDto.rol()){
            case "ADMIN":{
                r=rolService.getByNombre(Rol.RolNombre.ADMIN).orElse(null);
            } break;
            case "DBA":{
                r=rolService.getByNombre(Rol.RolNombre.DBA).orElse(null);
            } break;
            default:{
                r=rolService.getByNombre(Rol.RolNombre.USER).orElse(null);
            } break;
        }

        /*UsuarioRol u=new UsuarioRol();
        u.setRol(r);
        u.setUsuario(savedUser);
        iurService.save(u);
        

        iurService.save(UsuarioRol.builder()
                .usuario(savedUser)
                .rol(r)
                .build());
        return userMapper.toDTO(savedUser);
    }    */
}