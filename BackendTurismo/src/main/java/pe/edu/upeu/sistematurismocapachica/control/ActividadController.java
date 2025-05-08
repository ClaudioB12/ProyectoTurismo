package pe.edu.upeu.sistematurismocapachica.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Actividad guardar(@RequestBody Actividad actividad) {
        return actividadService.save(actividad);
    }

    @PutMapping("/editar")
    public Actividad editar(@RequestBody Actividad actividad) {
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
