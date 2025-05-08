package pe.edu.upeu.sistematurismocapachica.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sistematurismocapachica.modelo.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
