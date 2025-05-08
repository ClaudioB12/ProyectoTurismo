package pe.edu.upeu.sysgestionturismo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sysgestionturismo.modelo.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
