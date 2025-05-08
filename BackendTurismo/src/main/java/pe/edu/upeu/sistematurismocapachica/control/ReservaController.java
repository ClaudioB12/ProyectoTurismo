package pe.edu.upeu.sistematurismocapachica.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.modelo.Reserva;
import pe.edu.upeu.sistematurismocapachica.servicio.IReservaService;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    @GetMapping("/listar")
    public List<Reserva> listar() {
        return reservaService.findAll();
    }

    @PostMapping("/guardar")
    public Reserva guardar(@RequestBody Reserva reserva) {
        return reservaService.save(reserva);
    }

    @PutMapping("/editar")
    public Reserva editar(@RequestBody Reserva reserva) {
        return reservaService.update(reserva);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        reservaService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Reserva buscar(@PathVariable Long id) {
        return reservaService.findById(id);
    }
}