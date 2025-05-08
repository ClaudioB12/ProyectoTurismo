package pe.edu.upeu.sysgestionturismo.mappers;

import pe.edu.upeu.sysgestionturismo.dtos.ReservaDto;
import pe.edu.upeu.sysgestionturismo.modelo.Cliente;
import pe.edu.upeu.sysgestionturismo.modelo.PaqueteTuristico;
import pe.edu.upeu.sysgestionturismo.modelo.Reserva;

public class ReservaMapper {
    public static ReservaDto toDto(Reserva reserva) {
        ReservaDto dto = new ReservaDto();
        dto.setIdReserva(reserva.getIdReserva());
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setFechaInicio(reserva.getFechaInicio());
        dto.setFechaFin(reserva.getFechaFin());
        dto.setNumeroPersonas(reserva.getNumeroPersonas());
        dto.setIdCliente(reserva.getCliente() != null ? reserva.getCliente().getIdCliente() : null);
        dto.setIdPaquete(reserva.getPaqueteTuristico() != null ? reserva.getPaqueteTuristico().getIdPaquete() : null);
        return dto;
    }

    public static Reserva toEntity(ReservaDto dto) {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(dto.getIdReserva());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setFechaInicio(dto.getFechaInicio());
        reserva.setFechaFin(dto.getFechaFin());
        reserva.setNumeroPersonas(dto.getNumeroPersonas());

        if (dto.getIdCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(dto.getIdCliente());
            reserva.setCliente(cliente);
        }
        if (dto.getIdPaquete() != null) {
            PaqueteTuristico paquete = new PaqueteTuristico();
            paquete.setIdPaquete(dto.getIdPaquete());
            reserva.setPaqueteTuristico(paquete);
        }

        return reserva;
    }
}
