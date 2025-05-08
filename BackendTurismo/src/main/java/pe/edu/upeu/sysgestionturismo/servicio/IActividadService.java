package pe.edu.upeu.sysgestionturismo.servicio;

import pe.edu.upeu.sysgestionturismo.modelo.Actividad;

import java.util.List;

public interface IActividadService {
    Actividad save(Actividad actividad);
    Actividad update(Actividad actividad);
    void delete(Long id);
    Actividad findById(Long id);
    List<Actividad> findAll();
}
