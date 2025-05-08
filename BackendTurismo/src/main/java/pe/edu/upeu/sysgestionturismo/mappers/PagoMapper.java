package pe.edu.upeu.sysgestionturismo.mappers;

import pe.edu.upeu.sysgestionturismo.dtos.PagoDto;
import pe.edu.upeu.sysgestionturismo.modelo.Pago;
import pe.edu.upeu.sysgestionturismo.modelo.Reserva;

public class PagoMapper {
    public static PagoDto toDto(Pago pago) {
        PagoDto dto = new PagoDto();
        dto.setIdPago(pago.getIdPago());
        dto.setMonto(pago.getMonto());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setFechaPago(pago.getFechaPago());
        dto.setIdReserva(pago.getReserva() != null ? pago.getReserva().getIdReserva() : null);
        return dto;
    }

    public static Pago toEntity(PagoDto dto) {
        Pago pago = new Pago();
        pago.setIdPago(dto.getIdPago());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setFechaPago(dto.getFechaPago());

        if (dto.getIdReserva() != null) {
            Reserva reserva = new Reserva();
            reserva.setIdReserva(dto.getIdReserva());
            pago.setReserva(reserva);
        }
        return pago;
    }
}
