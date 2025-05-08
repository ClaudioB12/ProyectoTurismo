package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Hospedaje;
import pe.edu.upeu.sistematurismocapachica.repositorio.HospedajeRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IHospedajeService;

import java.util.List;

@Service
public class HospedajeServiceImpl implements IHospedajeService {

    @Autowired
    private HospedajeRepository hospedajeRepository;

    @Override
    public Hospedaje save(Hospedaje hospedaje) {
        return hospedajeRepository.save(hospedaje);
    }

    @Override
    public Hospedaje update(Hospedaje hospedaje) {
        return hospedajeRepository.save(hospedaje);
    }

    @Override
    public void delete(Long id) {
        hospedajeRepository.deleteById(id);
    }

    @Override
    public Hospedaje findById(Long id) {
        return hospedajeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Hospedaje> findAll() {
        return hospedajeRepository.findAll();
    }
}