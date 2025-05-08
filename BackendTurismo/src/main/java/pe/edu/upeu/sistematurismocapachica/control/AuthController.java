package pe.edu.upeu.sistematurismocapachica.control;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sistematurismocapachica.modelo.Usuario;
import pe.edu.upeu.sistematurismocapachica.security.JwtRequest;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody JwtRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContraseña())
            );
            
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            response.put("message", "Login exitoso");
            response.put("correo", request.getCorreo());
            response.put("status", true);
            response.put("token", jwtTokenUtil.generateToken(userDetails));
            
        } catch (Exception e) {
            response.put("message", "Credenciales incorrectas");
            response.put("status", false);
        }
        return response;
    }


    @PostMapping("/registrar")
    public String registrar(@RequestBody Usuario usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuarioService.save(usuario);
        return "Usuario registrado exitosamente!";
    }
}