package pe.edu.upeu.sistematurismocapachica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.dtos.PaqueteTuristicoDto;
import pe.edu.upeu.sistematurismocapachica.mappers.PaqueteTuristicoMapper;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;
import pe.edu.upeu.sistematurismocapachica.modelo.PaqueteTuristico;
import pe.edu.upeu.sistematurismocapachica.repositorio.ActividadRepository;
import pe.edu.upeu.sistematurismocapachica.repositorio.DestinoRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IPaqueteTuristicoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/paquete")
@CrossOrigin(origins = "*")
public class PaqueteTuristicoController {

    @Autowired
    private IPaqueteTuristicoService paqueteTuristicoService;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private DestinoRepository destinoRepository;

    @GetMapping("/listar")
    public List<PaqueteTuristicoDto> listar() {
        return paqueteTuristicoService.findAll().stream()
                .map(PaqueteTuristicoMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/guardar")
    public PaqueteTuristicoDto guardar(@RequestBody PaqueteTuristicoDto dto) {
        List<Actividad> actividades = actividadRepository.findAll();
        List<Destino> destinos = destinoRepository.findAll();

        PaqueteTuristico paquete = PaqueteTuristicoMapper.toEntity(dto, actividades, destinos);
        PaqueteTuristico saved = paqueteTuristicoService.save(paquete);
        return PaqueteTuristicoMapper.toDto(saved);
    }

    @PutMapping("/editar")
    public PaqueteTuristicoDto editar(@RequestBody PaqueteTuristicoDto dto) {
        List<Actividad> actividades = actividadRepository.findAll();
        List<Destino> destinos = destinoRepository.findAll();

        PaqueteTuristico paquete = PaqueteTuristicoMapper.toEntity(dto, actividades, destinos);
        PaqueteTuristico updated = paqueteTuristicoService.update(paquete);
        return PaqueteTuristicoMapper.toDto(updated);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        paqueteTuristicoService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public PaqueteTuristicoDto buscar(@PathVariable Long id) {
        PaqueteTuristico paquete = paqueteTuristicoService.findById(id);
        return paquete != null ? PaqueteTuristicoMapper.toDto(paquete) : null;
    }
}
