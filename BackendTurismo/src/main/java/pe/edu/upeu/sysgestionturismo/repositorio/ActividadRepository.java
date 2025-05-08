package pe.edu.upeu.sysgestionturismo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sysgestionturismo.modelo.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
