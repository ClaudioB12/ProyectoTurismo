package pe.edu.upeu.sysgestionturismo.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservaDto {
    private Long idReserva;
    private LocalDate fechaReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer numeroPersonas;
    private Long idCliente;
    private Long idPaquete;
}
