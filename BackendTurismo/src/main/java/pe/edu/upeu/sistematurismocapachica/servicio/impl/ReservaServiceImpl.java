package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Cliente;
import pe.edu.upeu.sistematurismocapachica.modelo.PaqueteTuristico;
import pe.edu.upeu.sistematurismocapachica.modelo.Reserva;
import pe.edu.upeu.sistematurismocapachica.repositorio.ClienteRepository;
import pe.edu.upeu.sistematurismocapachica.repositorio.PaqueteTuristicoRepository;
import pe.edu.upeu.sistematurismocapachica.repositorio.ReservaRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IReservaService;

import java.util.List;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PaqueteTuristicoRepository paqueteTuristicoRepository;

    @Override
    public Reserva save(Reserva reserva) {
        // Verifica y asocia el cliente si existe
        if (reserva.getCliente() != null && reserva.getCliente().getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(reserva.getCliente().getIdCliente()).orElse(null);
            reserva.setCliente(cliente);
        } else {
            reserva.setCliente(null);
        }

        // Verifica y asocia el paquete turístico si existe
        if (reserva.getPaqueteTuristico() != null && reserva.getPaqueteTuristico().getIdPaquete() != null) {
            PaqueteTuristico paquete = paqueteTuristicoRepository.findById(reserva.getPaqueteTuristico().getIdPaquete()).orElse(null);
            reserva.setPaqueteTuristico(paquete);
        } else {
            reserva.setPaqueteTuristico(null);
        }

        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva update(Reserva reserva) {
        return save(reserva); // Reutiliza la lógica de save para actualizar
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
