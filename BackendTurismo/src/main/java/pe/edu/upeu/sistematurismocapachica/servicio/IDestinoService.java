package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Destino;

import java.util.List;

public interface IDestinoService {
    Destino save(Destino destino);
    Destino update(Destino destino);
    void delete(Long id);
    Destino findById(Long id);
    List<Destino> findAll();
}
