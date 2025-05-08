package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;

import java.util.List;

public interface IActividadService {
    Actividad save(Actividad actividad);
    Actividad update(Actividad actividad);
    void delete(Long id);
    Actividad findById(Long id);
    List<Actividad> findAll();
}
