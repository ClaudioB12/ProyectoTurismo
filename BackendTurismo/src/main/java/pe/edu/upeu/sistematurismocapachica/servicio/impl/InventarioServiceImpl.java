package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Inventario;
import pe.edu.upeu.sistematurismocapachica.repositorio.InventarioRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IInventarioService;

import java.util.List;

@Service
public class InventarioServiceImpl implements IInventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Override
    public Inventario update(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Override
    public void delete(Long id) {
        inventarioRepository.deleteById(id);
    }

    @Override
    public Inventario findById(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }
}