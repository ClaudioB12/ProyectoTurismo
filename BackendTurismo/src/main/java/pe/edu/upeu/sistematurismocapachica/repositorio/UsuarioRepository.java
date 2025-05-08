package pe.edu.upeu.sistematurismocapachica.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
