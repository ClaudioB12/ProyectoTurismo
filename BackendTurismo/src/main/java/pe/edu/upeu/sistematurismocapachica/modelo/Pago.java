package pe.edu.upeu.sistematurismocapachica.modelo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private Double monto;
    private String metodoPago; // Ej: VISA, transferencia
    private LocalDateTime fechaPago;

    @OneToOne
    @JoinColumn(name = "idReserva")
    private Reserva reserva;
}