package pe.edu.upeu.sistematurismocapachica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;
import pe.edu.upeu.sistematurismocapachica.servicio.IDestinoService;

import java.util.List;

@RestController
@RequestMapping("/api/destino")
@CrossOrigin(origins = "*")
public class DestinoController {

    @Autowired
    private IDestinoService destinoService;

    @GetMapping("/listar")
    public List<Destino> listar() {
        return destinoService.findAll();
    }

    @PostMapping("/guardar")
    public Destino guardar(@RequestBody Destino destino) {
        return destinoService.save(destino);
    }

    @PutMapping("/editar")
    public Destino editar(@RequestBody Destino destino) {
        return destinoService.update(destino);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        destinoService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Destino buscar(@PathVariable Long id) {
        return destinoService.findById(id);
    }
}