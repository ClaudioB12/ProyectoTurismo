package pe.edu.upeu.sistematurismocapachica.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
