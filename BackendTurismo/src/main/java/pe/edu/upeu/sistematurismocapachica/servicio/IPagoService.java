package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Pago;

import java.util.List;

public interface IPagoService {
    Pago save(Pago pago);
    Pago update(Pago pago);
    void delete(Long id);
    Pago findById(Long id);
    List<Pago> findAll();
}
