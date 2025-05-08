package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Resena;

import java.util.List;

public interface IResenaService {
    Resena save(Resena resena);
    Resena update(Resena resena);
    void delete(Long id);
    Resena findById(Long id);
    List<Resena> findAll();
}
