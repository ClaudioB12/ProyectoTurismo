package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Hospedaje;

import java.util.List;

public interface IHospedajeService {
    Hospedaje save(Hospedaje hospedaje);
    Hospedaje update(Hospedaje hospedaje);
    void delete(Long id);
    Hospedaje findById(Long id);
    List<Hospedaje> findAll();
}
