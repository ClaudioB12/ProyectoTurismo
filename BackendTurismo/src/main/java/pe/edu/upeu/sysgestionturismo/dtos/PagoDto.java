package pe.edu.upeu.sysgestionturismo.dtos;

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
