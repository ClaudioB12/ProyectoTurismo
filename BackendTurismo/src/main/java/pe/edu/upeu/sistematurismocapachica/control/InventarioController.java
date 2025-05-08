package pe.edu.upeu.sistematurismocapachica.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.modelo.Inventario;
import pe.edu.upeu.sistematurismocapachica.servicio.IInventarioService;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private IInventarioService inventarioService;

    @GetMapping("/listar")
    public List<Inventario> listar() {
        return inventarioService.findAll();
    }

    @PostMapping("/guardar")
    public Inventario guardar(@RequestBody Inventario inventario) {
        return inventarioService.save(inventario);
    }

    @PutMapping("/editar")
    public Inventario editar(@RequestBody Inventario inventario) {
        return inventarioService.update(inventario);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        inventarioService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Inventario buscar(@PathVariable Long id) {
        return inventarioService.findById(id);
    }
}