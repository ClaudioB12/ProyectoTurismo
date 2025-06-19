package pe.edu.upeu.sistematurismocapachica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.modelo.Pago;
import pe.edu.upeu.sistematurismocapachica.servicio.IPagoService;

import java.util.List;

@RestController
@RequestMapping("/api/pago")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private IPagoService pagoService;

    @GetMapping("/listar")
    public List<Pago> listar() {
        return pagoService.findAll();
    }

    @PostMapping("/guardar")
    public Pago guardar(@RequestBody Pago pago) {
        return pagoService.save(pago);
    }

    @PutMapping("/editar")
    public Pago editar(@RequestBody Pago pago) {
        return pagoService.update(pago);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        pagoService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Pago buscar(@PathVariable Long id) {
        return pagoService.findById(id);
    }
}
