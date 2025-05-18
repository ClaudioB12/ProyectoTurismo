package pe.edu.upeu.sistematurismocapachica.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.dtos.ClienteDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Cliente;
import pe.edu.upeu.sistematurismocapachica.servicio.IClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/listar")
    public List<Cliente> listar() {
        return clienteService.findAll();
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody ClienteDto clienteDto) {
        try {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(clienteDto.getIdCliente());
            cliente.setNombreCompleto(clienteDto.getNombreCompleto());
            cliente.setCorreo(clienteDto.getCorreo()); // muy importante
            cliente.setTelefono(clienteDto.getTelefono());
            cliente.setDireccion(clienteDto.getDireccion());

            Cliente guardado = clienteService.save(cliente);

            return ResponseEntity.status(201).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar cliente: " + e.getMessage());
        }
    }


    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody ClienteDto clienteDto) {
        try {
            Cliente clienteExistente = clienteService.findById(clienteDto.getIdCliente());
            if (clienteExistente == null) {
                return ResponseEntity.badRequest().body("Cliente no encontrado");
            }

            clienteExistente.setNombreCompleto(clienteDto.getNombreCompleto());
            clienteExistente.setCorreo(clienteDto.getCorreo());
            clienteExistente.setTelefono(clienteDto.getTelefono());
            clienteExistente.setDireccion(clienteDto.getDireccion());

            Cliente actualizado = clienteService.update(clienteExistente);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al editar cliente: " + e.getMessage());
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    // Nuevo endpoint para actualizar datos del cliente autenticado
    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarDatosCliente(@RequestBody Cliente clienteDatos,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String correo = userDetails.getUsername();  // correo del usuario autenticado

            Cliente clienteExistente = clienteService.findByCorreo(correo);
            if (clienteExistente == null) {
                return ResponseEntity.badRequest().body("Cliente no existe para el usuario autenticado");
            }

            // Actualizar solo los campos que quieres permitir modificar
            clienteExistente.setNombreCompleto(clienteDatos.getNombreCompleto());
            clienteExistente.setTelefono(clienteDatos.getTelefono());
            clienteExistente.setDireccion(clienteDatos.getDireccion());

            Cliente actualizado = clienteService.update(clienteExistente);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar cliente: " + e.getMessage());
        }
    }
}
