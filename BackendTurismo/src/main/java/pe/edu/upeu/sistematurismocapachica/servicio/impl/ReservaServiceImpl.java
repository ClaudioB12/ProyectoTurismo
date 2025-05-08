package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Reserva;
import pe.edu.upeu.sistematurismocapachica.repositorio.ReservaRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IReservaService;

import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva update(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }

    @Override
    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }
}