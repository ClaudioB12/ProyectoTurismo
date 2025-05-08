package pe.edu.upeu.sysgestionturismo.servicio;

import pe.edu.upeu.sysgestionturismo.modelo.Cliente;

import java.util.List;

public interface IClienteService {
    Cliente save(Cliente cliente);
    Cliente update(Cliente cliente);
    void delete(Long id);
    Cliente findById(Long id);
    List<Cliente> findAll();
}