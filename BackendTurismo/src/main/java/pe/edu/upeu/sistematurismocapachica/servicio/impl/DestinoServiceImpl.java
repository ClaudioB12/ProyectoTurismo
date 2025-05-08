package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;
import pe.edu.upeu.sistematurismocapachica.repositorio.DestinoRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IDestinoService;

import java.util.List;

@Service
public class DestinoServiceImpl implements IDestinoService {

    @Autowired
    private DestinoRepository destinoRepository;

    @Override
    public Destino save(Destino destino) {
        return destinoRepository.save(destino);
    }

    @Override
    public Destino update(Destino destino) {
        return destinoRepository.save(destino);
    }

    @Override
    public void delete(Long id) {
        destinoRepository.deleteById(id);
    }

    @Override
    public Destino findById(Long id) {
        return destinoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Destino> findAll() {
        return destinoRepository.findAll();
    }
}