package pe.edu.upeu.sistematurismocapachica.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sistematurismocapachica.modelo.Hospedaje;

import java.util.List;

public interface HospedajeRepository extends JpaRepository<Hospedaje, Long> {
    List<Hospedaje> findByDestinoIdDestino(Long idDestino);

}
