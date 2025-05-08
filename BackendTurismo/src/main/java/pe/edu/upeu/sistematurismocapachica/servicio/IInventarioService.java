package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Inventario;

import java.util.List;

public interface IInventarioService {
    Inventario save(Inventario inventario);
    Inventario update(Inventario inventario);
    void delete(Long id);
    Inventario findById(Long id);
    List<Inventario> findAll();
}
