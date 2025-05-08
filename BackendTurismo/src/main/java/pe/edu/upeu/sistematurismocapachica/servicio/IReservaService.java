package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Reserva;

import java.util.List;

public interface IReservaService {
    Reserva save(Reserva reserva);
    Reserva update(Reserva reserva);
    void delete(Long id);
    Reserva findById(Long id);
    List<Reserva> findAll();
}
