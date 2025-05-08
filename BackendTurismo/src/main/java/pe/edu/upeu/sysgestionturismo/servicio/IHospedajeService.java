package pe.edu.upeu.sysgestionturismo.servicio;

import pe.edu.upeu.sysgestionturismo.modelo.Hospedaje;

import java.util.List;

public interface IHospedajeService {
    Hospedaje save(Hospedaje hospedaje);
    Hospedaje update(Hospedaje hospedaje);
    void delete(Long id);
    Hospedaje findById(Long id);
    List<Hospedaje> findAll();
}
