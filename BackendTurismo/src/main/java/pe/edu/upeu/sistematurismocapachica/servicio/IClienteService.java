package pe.edu.upeu.sistematurismocapachica.servicio;

import pe.edu.upeu.sistematurismocapachica.modelo.Cliente;

import java.util.List;

public interface IClienteService {
    Cliente save(Cliente cliente);
    Cliente update(Cliente cliente);
    void delete(Long id);
    Cliente findById(Long id);
    List<Cliente> findAll();
    Cliente findByCorreo(String correo);
}