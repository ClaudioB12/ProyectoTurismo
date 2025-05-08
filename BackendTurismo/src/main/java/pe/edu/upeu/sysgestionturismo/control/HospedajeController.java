package pe.edu.upeu.sysgestionturismo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysgestionturismo.modelo.Hospedaje;
import pe.edu.upeu.sysgestionturismo.servicio.IHospedajeService;

import java.util.List;

@RestController
@RequestMapping("/api/hospedaje")
@CrossOrigin(origins = "*")
public class HospedajeController {

    @Autowired
    private IHospedajeService hospedajeService;

    @GetMapping("/listar")
    public List<Hospedaje> listar() {
        return hospedajeService.findAll();
    }

    @PostMapping("/guardar")
    public Hospedaje guardar(@RequestBody Hospedaje hospedaje) {
        return hospedajeService.save(hospedaje);
    }

    @PutMapping("/editar")
    public Hospedaje editar(@RequestBody Hospedaje hospedaje) {
        return hospedajeService.update(hospedaje);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        hospedajeService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Hospedaje buscar(@PathVariable Long id) {
        return hospedajeService.findById(id);
    }
}