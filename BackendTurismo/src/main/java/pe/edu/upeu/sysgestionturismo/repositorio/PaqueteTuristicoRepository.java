package pe.edu.upeu.sysgestionturismo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sysgestionturismo.modelo.PaqueteTuristico;

public interface PaqueteTuristicoRepository extends JpaRepository<PaqueteTuristico, Long> {
}
