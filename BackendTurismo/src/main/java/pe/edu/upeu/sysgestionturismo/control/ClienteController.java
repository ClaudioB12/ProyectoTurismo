package pe.edu.upeu.sysgestionturismo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysgestionturismo.modelo.Cliente;
import pe.edu.upeu.sysgestionturismo.servicio.IClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/listar")
    public List<Cliente> listar() {
        return clienteService.findAll();
    }

    @PostMapping("/guardar")
    public Cliente guardar(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/editar")
    public Cliente editar(@RequestBody Cliente cliente) {
        return clienteService.update(cliente);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return clienteService.findById(id);
    }
}