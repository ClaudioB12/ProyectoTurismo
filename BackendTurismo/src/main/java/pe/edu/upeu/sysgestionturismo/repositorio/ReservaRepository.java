package pe.edu.upeu.sysgestionturismo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sysgestionturismo.modelo.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
