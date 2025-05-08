package pe.edu.upeu.sysgestionturismo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sysgestionturismo.modelo.Hospedaje;

public interface HospedajeRepository extends JpaRepository<Hospedaje, Long> {
}
