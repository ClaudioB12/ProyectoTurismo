package pe.edu.upeu.sistematurismocapachica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.dtos.HospedajeDto;
import pe.edu.upeu.sistematurismocapachica.mappers.HospedajeMapper;
import pe.edu.upeu.sistematurismocapachica.modelo.Hospedaje;
import pe.edu.upeu.sistematurismocapachica.servicio.IHospedajeService;

import java.util.List;

@RestController
@RequestMapping("/api/hospedaje")
@CrossOrigin(origins = "*")
public class HospedajeController {

    @Autowired
    private IHospedajeService hospedajeService;

    @GetMapping("/listar")
    public List<Hospedaje> listar() {
        return hospedajeService.findAll(); // 👈 devuelve ENTIDADES con objeto destino anidado
    }

    @PostMapping("/guardar")
    public HospedajeDto guardar(@RequestBody HospedajeDto hospedajeDto) {
        Hospedaje hospedaje = HospedajeMapper.toEntity(hospedajeDto);
        Hospedaje guardado = hospedajeService.save(hospedaje);
        return HospedajeMapper.toDto(guardado);
    }

    @PutMapping("/editar")
    public Hospedaje editar(@RequestBody HospedajeDto hospedajeDto) {
        Hospedaje hospedaje = HospedajeMapper.toEntity(hospedajeDto);
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
