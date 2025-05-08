package pe.edu.upeu.sistematurismocapachica.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sistematurismocapachica.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}