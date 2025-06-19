package pe.edu.upeu.sistematurismocapachica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.dtos.ActividadDto;
import pe.edu.upeu.sistematurismocapachica.mappers.ActividadMapper;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;
import pe.edu.upeu.sistematurismocapachica.servicio.IActividadService;

import java.util.List;

@RestController
@RequestMapping("/api/actividad")
@CrossOrigin(origins = "*")
public class ActividadController {

    @Autowired
    private IActividadService actividadService;

    @GetMapping("/listar")
    public List<Actividad> listar() {
        return actividadService.findAll();
    }

    @PostMapping("/guardar")
    public ActividadDto guardar(@RequestBody ActividadDto actividadDto) {
        System.out.println("ID DESTINO RECIBIDO: " + actividadDto.getIdDestino());

        Actividad actividad = ActividadMapper.toEntity(actividadDto);
        Actividad actividadGuardada = actividadService.save(actividad);

        return ActividadMapper.toDto(actividadGuardada);
    }

    @PutMapping("/editar")
    public Actividad editar(@RequestBody ActividadDto actividadDto) {
        System.out.println("ID DESTINO EDITADO: " + actividadDto.getIdDestino());
        Actividad actividad = ActividadMapper.toEntity(actividadDto);
        return actividadService.update(actividad);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        actividadService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Actividad buscar(@PathVariable Long id) {
        return actividadService.findById(id);
    }
}
