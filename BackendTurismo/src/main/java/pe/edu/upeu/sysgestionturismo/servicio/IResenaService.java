package pe.edu.upeu.sysgestionturismo.servicio;

import pe.edu.upeu.sysgestionturismo.modelo.Resena;

import java.util.List;

public interface IResenaService {
    Resena save(Resena resena);
    Resena update(Resena resena);
    void delete(Long id);
    Resena findById(Long id);
    List<Resena> findAll();
}
