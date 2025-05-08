package pe.edu.upeu.sistematurismocapachica.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;
import pe.edu.upeu.sistematurismocapachica.repositorio.UsuarioRepository;

//Clase S4
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getCorreo().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContraseña())
                .roles(usuario.getRol())
                .build();
    }
}