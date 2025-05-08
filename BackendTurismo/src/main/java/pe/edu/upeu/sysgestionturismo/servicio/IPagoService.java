package pe.edu.upeu.sysgestionturismo.servicio;

import pe.edu.upeu.sysgestionturismo.modelo.Pago;

import java.util.List;

public interface IPagoService {
    Pago save(Pago pago);
    Pago update(Pago pago);
    void delete(Long id);
    Pago findById(Long id);
    List<Pago> findAll();
}
