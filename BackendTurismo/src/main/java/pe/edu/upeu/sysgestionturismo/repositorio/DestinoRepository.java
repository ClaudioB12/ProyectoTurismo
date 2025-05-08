package pe.edu.upeu.sysgestionturismo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sysgestionturismo.modelo.Destino;

public interface DestinoRepository extends JpaRepository<Destino, Long> {
}
