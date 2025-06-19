package pe.edu.upeu.sistematurismocapachica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.sistematurismocapachica.dtos.ClienteDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Cliente;
import pe.edu.upeu.sistematurismocapachica.servicio.IClienteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/perfil")
    public ResponseEntity<ClienteDto> obtenerPerfilCliente(Authentication authentication) {
        String email = authentication.getName(); // el 'subject' del token JWT

        Cliente cliente = clienteService.findByCorreo(email); // método que tú implementas
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        ClienteDto dto = new ClienteDto();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombreCompleto(cliente.getNombreCompleto());
        dto.setCorreo(cliente.getCorreo());
        // ...otros campos que desees exponer

        return ResponseEntity.ok(dto);
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

    @PostMapping("/subirFoto")
    public ResponseEntity<?> subirFotoPerfil(@RequestParam("file") MultipartFile file,
                                             Authentication authentication) {
        String email = authentication.getName();
        Cliente cliente = clienteService.findByCorreo(email);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }

        try {
            // Guardar el archivo en disco o almacenamiento
            String nombreArchivo = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path ruta = Paths.get("uploads/fotos", nombreArchivo);
            Files.createDirectories(ruta.getParent());
            Files.write(ruta, file.getBytes());

            // Guardar la ruta en el cliente
            cliente.setFotoPerfilUrl("/uploads/fotos/" + nombreArchivo);
            clienteService.update(cliente);

            return ResponseEntity.ok("Foto subida correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la foto: " + e.getMessage());
        }
    }

}
