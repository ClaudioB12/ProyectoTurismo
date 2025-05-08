package pe.edu.upeu.sysgestionturismo.servicio;

import pe.edu.upeu.sysgestionturismo.modelo.Usuario;

import java.util.List;
import pe.edu.upeu.sysgestionturismo.dtos.UsuarioDto;

public interface IUsuarioService {
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    void delete(Long id);
    Usuario findById(Long id);
    List<Usuario> findAll();
   /* UsuarioDto login(UsuarioDto.CredencialesDto credentialsDto);
    UsuarioDto register(UsuarioDto.UsuarioCrearDto userDto);    */
}
