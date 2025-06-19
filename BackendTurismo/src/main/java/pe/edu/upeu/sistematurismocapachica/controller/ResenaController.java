package pe.edu.upeu.sistematurismocapachica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.modelo.Resena;
import pe.edu.upeu.sistematurismocapachica.servicio.IResenaService;

import java.util.List;

@RestController
@RequestMapping("/api/resena")
@CrossOrigin(origins = "*")
public class ResenaController {
    @Autowired
    private IResenaService resenaService;

    @GetMapping("/listar")
    public List<Resena> listar() {
        return resenaService.findAll();
    }

    @PostMapping("/guardar")
    public Resena guardar(@RequestBody Resena resena) {
        return resenaService.save(resena);
    }

    @PutMapping("/editar")
    public Resena editar(@RequestBody Resena resena) {
        return resenaService.update(resena);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        resenaService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Resena buscar(@PathVariable Long id) {
        return resenaService.findById(id);
    }
}