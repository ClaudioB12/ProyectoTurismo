package pe.edu.upeu.sistematurismocapachica.control;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.modelo.Cliente;
import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;
import pe.edu.upeu.sistematurismocapachica.security.JwtRequest;
import pe.edu.upeu.sistematurismocapachica.servicio.IClienteService;
import pe.edu.upeu.sistematurismocapachica.servicio.IUsuarioService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upeu.sistematurismocapachica.security.JwtTokenUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IClienteService clienteService;  // <-- Inyección de dependencia correcta

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody JwtRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getClave())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            Cliente cliente = clienteService.findByCorreo(request.getCorreo());

            if (cliente == null) {
                // Cliente no existe -> debe completar sus datos
                response.put("message", "Login exitoso, cliente no registrado");
                response.put("correo", request.getCorreo());
                response.put("status", true);
                response.put("token", jwtTokenUtil.generateToken(userDetails));
                response.put("clienteExiste", false);
                response.put("datosClienteCompletos", false);
            } else {
                // Cliente existe, verificamos si sus datos están completos
                boolean datosCompletos = cliente.getNombreCompleto() != null && !cliente.getNombreCompleto().isEmpty()
                        && cliente.getTelefono() != null && !cliente.getTelefono().isEmpty()
                        && cliente.getDireccion() != null && !cliente.getDireccion().isEmpty();

                response.put("message", "Login exitoso");
                response.put("correo", request.getCorreo());
                response.put("status", true);
                response.put("token", jwtTokenUtil.generateToken(userDetails));
                response.put("clienteExiste", true);
                response.put("datosClienteCompletos", datosCompletos);
            }

        } catch (Exception e) {
            response.put("message", "Credenciales incorrectas");
            response.put("status", false);
        }
        return response;
    }


    @PostMapping("/registrar")
    public String registrar(@RequestBody Usuario usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        usuarioService.save(usuario);
        return "Usuario registrado exitosamente!";
    }
}
