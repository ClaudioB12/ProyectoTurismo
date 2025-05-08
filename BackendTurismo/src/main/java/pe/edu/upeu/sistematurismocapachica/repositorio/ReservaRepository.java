package pe.edu.upeu.sistematurismocapachica.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sistematurismocapachica.modelo.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
