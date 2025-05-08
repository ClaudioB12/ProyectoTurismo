package pe.edu.upeu.sistematurismocapachica.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PagoDto {
    private Long idPago;
    private Double monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private Long idReserva;
}
