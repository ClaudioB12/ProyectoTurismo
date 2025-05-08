package pe.edu.upeu.sysgestionturismo.servicio;

import pe.edu.upeu.sysgestionturismo.modelo.Inventario;

import java.util.List;

public interface IInventarioService {
    Inventario save(Inventario inventario);
    Inventario update(Inventario inventario);
    void delete(Long id);
    Inventario findById(Long id);
    List<Inventario> findAll();
}
