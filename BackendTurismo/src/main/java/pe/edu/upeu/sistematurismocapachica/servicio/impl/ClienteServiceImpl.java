package pe.edu.upeu.sistematurismocapachica.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sistematurismocapachica.modelo.Cliente;
import pe.edu.upeu.sistematurismocapachica.repositorio.ClienteRepository;
import pe.edu.upeu.sistematurismocapachica.servicio.IClienteService;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
}