package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.PaqueteTuristico;
import pe.edu.upeu.sistematurismocapachica.repositorio.PaqueteTuristicoRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IPaqueteTuristicoService;

import java.util.List;

@Service
public class PaqueteTuristicoServiceImpl implements IPaqueteTuristicoService {

    @Autowired
    private PaqueteTuristicoRepository paqueteTuristicoRepository;

    @Override
    public PaqueteTuristico save(PaqueteTuristico paquete) {
        return paqueteTuristicoRepository.save(paquete);
    }

    @Override
    public PaqueteTuristico update(PaqueteTuristico paquete) {
        return paqueteTuristicoRepository.save(paquete);
    }

    @Override
    public void delete(Long id) {
        paqueteTuristicoRepository.deleteById(id);
    }

    @Override
    public PaqueteTuristico findById(Long id) {
        return paqueteTuristicoRepository.findById(id).orElse(null);
    }

    @Override
    public List<PaqueteTuristico> findAll() {
        return paqueteTuristicoRepository.findAll();
    }
}
