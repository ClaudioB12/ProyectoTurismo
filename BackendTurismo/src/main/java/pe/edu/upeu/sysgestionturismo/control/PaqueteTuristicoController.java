package pe.edu.upeu.sysgestionturismo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysgestionturismo.modelo.PaqueteTuristico;
import pe.edu.upeu.sysgestionturismo.servicio.IPaqueteTuristicoService;

import java.util.List;

@RestController
@RequestMapping("/api/paquete")
@CrossOrigin(origins = "*")
public class PaqueteTuristicoController {

    @Autowired
    private IPaqueteTuristicoService paqueteTuristicoService;

    @GetMapping("/listar")
    public List<PaqueteTuristico> listar() {
        return paqueteTuristicoService.findAll();
    }

    @PostMapping("/guardar")
    public PaqueteTuristico guardar(@RequestBody PaqueteTuristico paquete) {
        return paqueteTuristicoService.save(paquete);
    }

    @PutMapping("/editar")
    public PaqueteTuristico editar(@RequestBody PaqueteTuristico paquete) {
        return paqueteTuristicoService.update(paquete);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        paqueteTuristicoService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public PaqueteTuristico buscar(@PathVariable Long id) {
        return paqueteTuristicoService.findById(id);
    }
}