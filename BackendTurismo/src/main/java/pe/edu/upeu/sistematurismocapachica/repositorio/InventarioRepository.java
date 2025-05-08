package pe.edu.upeu.sistematurismocapachica.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sistematurismocapachica.modelo.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
