package pe.edu.upeu.sysgestionturismo.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysgestionturismo.modelo.Pago;
import pe.edu.upeu.sysgestionturismo.repositorio.PagoRepository;
import pe.edu.upeu.sysgestionturismo.servicio.IPagoService;

import java.util.List;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public Pago update(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }

    @Override
    public Pago findById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }
}