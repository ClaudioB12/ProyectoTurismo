package pe.edu.upeu.sysgestionturismo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sysgestionturismo.modelo.Usuario;
import pe.edu.upeu.sysgestionturismo.servicio.IUsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/listar")
    public List<Usuario> listar() {
        return usuarioService.findAll();
    }

    @PostMapping("/guardar")
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/editar")
    public Usuario editar(@RequestBody Usuario usuario) {
        return usuarioService.update(usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Usuario buscar(@PathVariable Long id) {
        return usuarioService.findById(id);
    }
}
