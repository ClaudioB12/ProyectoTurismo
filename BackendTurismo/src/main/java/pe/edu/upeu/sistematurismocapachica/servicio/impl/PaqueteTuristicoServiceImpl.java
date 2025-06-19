package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;
import pe.edu.upeu.sistematurismocapachica.modelo.PaqueteTuristico;
import pe.edu.upeu.sistematurismocapachica.repositorio.ActividadRepository;
import pe.edu.upeu.sistematurismocapachica.repositorio.DestinoRepository;
import pe.edu.upeu.sistematurismocapachica.repositorio.PaqueteTuristicoRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IPaqueteTuristicoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaqueteTuristicoServiceImpl implements IPaqueteTuristicoService {

    @Autowired
    private DestinoRepository destinoRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private PaqueteTuristicoRepository paqueteTuristicoRepository;

    @Override
    public PaqueteTuristico save(PaqueteTuristico paquete) {
        if (paquete.getDestino() != null && paquete.getDestino().getIdDestino() != null) {
            paquete.setDestino(destinoRepository.findById(paquete.getDestino().getIdDestino()).orElse(null));
        }

        if (paquete.getActividades() != null && !paquete.getActividades().isEmpty()) {
            List<Actividad> actividades = paquete.getActividades().stream()
                    .map(a -> actividadRepository.findById(a.getIdActividad()).orElse(null))
                    .filter(a -> a != null)
                    .collect(Collectors.toList());
            paquete.setActividades(actividades);
        }

        return paqueteTuristicoRepository.save(paquete);
    }


    @Override
    public PaqueteTuristico update(PaqueteTuristico paquete) {
        // Verificamos si existe el paquete
        PaqueteTuristico paqueteExistente = paqueteTuristicoRepository.findById(paquete.getIdPaquete()).orElse(null);
        if (paqueteExistente == null) {
            return null; // o puedes lanzar una excepción si prefieres
        }

        // Actualizamos los campos básicos
        paqueteExistente.setNombre(paquete.getNombre());
        paqueteExistente.setDescripcion(paquete.getDescripcion());
        paqueteExistente.setPrecioTotal(paquete.getPrecioTotal());

        // Actualizamos el destino si tiene un ID válido
        if (paquete.getDestino() != null && paquete.getDestino().getIdDestino() != null) {
            destinoRepository.findById(paquete.getDestino().getIdDestino()).ifPresent(paqueteExistente::setDestino);
        }

        // Actualizamos la lista de actividades
        if (paquete.getActividades() != null && !paquete.getActividades().isEmpty()) {
            List<Actividad> actividadesActualizadas = paquete.getActividades().stream()
                    .map(act -> actividadRepository.findById(act.getIdActividad()).orElse(null))
                    .filter(act -> act != null)
                    .collect(Collectors.toList());

            paqueteExistente.setActividades(actividadesActualizadas);
        } else {
            paqueteExistente.setActividades(null); // Limpiar si ya no hay actividades
        }

        return paqueteTuristicoRepository.save(paqueteExistente);
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
