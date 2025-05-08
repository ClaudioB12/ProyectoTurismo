package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;
import pe.edu.upeu.sistematurismocapachica.repositorio.ActividadRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IActividadService;

import java.util.List;

@Service
public class ActividadServiceImpl implements IActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Override
    public Actividad save(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public Actividad update(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public void delete(Long id) {
        actividadRepository.deleteById(id);
    }

    @Override
    public Actividad findById(Long id) {
        return actividadRepository.findById(id).orElse(null);
    }

    @Override
    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }
}