package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    void delete(Long id);
    Usuario findById(Long id);
    List<Usuario> findAll();
   /* UsuarioDto login(UsuarioDto.CredencialesDto credentialsDto);
    UsuarioDto register(UsuarioDto.UsuarioCrearDto userDto);    */
}
