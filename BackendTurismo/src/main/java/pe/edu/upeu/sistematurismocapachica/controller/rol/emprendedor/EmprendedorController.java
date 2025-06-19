package pe.edu.upeu.sistematurismocapachica.controller.rol.emprendedor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emprendedor")
@PreAuthorize("hasRole('EMPRENDEDOR')")
public class EmprendedorController {
    @GetMapping("/dashboard")
    public String emprendedorDashboard() {
        return "Bienvenido Emprendedor";
    }
}