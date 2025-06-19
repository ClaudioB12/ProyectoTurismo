package pe.edu.upeu.sistematurismocapachica.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;

import java.util.Collection;
import java.util.Collections;

public class JwtUserDetails implements UserDetails {

    private final Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Asignar rol con prefijo ROLE_
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
    }

    @Override
    public String getPassword() {
        return usuario.getClave();
    }

    @Override
    public String getUsername() {
        return usuario.getCorreo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
