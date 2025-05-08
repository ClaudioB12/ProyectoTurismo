package pe.edu.upeu.sistematurismocapachica.mappers;

import pe.edu.upeu.sistematurismocapachica.dtos.PaqueteTuristicoDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;
import pe.edu.upeu.sistematurismocapachica.modelo.PaqueteTuristico;

import java.util.stream.Collectors;

public class PaqueteTuristicoMapper {
    public static PaqueteTuristicoDto toDto(PaqueteTuristico paquete) {
        PaqueteTuristicoDto dto = new PaqueteTuristicoDto();
        dto.setIdPaquete(paquete.getIdPaquete());
        dto.setNombre(paquete.getNombre());
        dto.setDescripcion(paquete.getDescripcion());
        dto.setPrecioTotal(paquete.getPrecioTotal());
        if (paquete.getActividades() != null) {
            dto.setActividadesIds(paquete.getActividades().stream()
                    .map(Actividad::getIdActividad)
                    .collect(Collectors.toList()));
        }
        dto.setIdDestino(paquete.getDestino() != null ? paquete.getDestino().getIdDestino() : null);
        return dto;
    }

    public static PaqueteTuristico toEntity(PaqueteTuristicoDto dto) {
        PaqueteTuristico paquete = new PaqueteTuristico();
        paquete.setIdPaquete(dto.getIdPaquete());
        paquete.setNombre(dto.getNombre());
        paquete.setDescripcion(dto.getDescripcion());
        paquete.setPrecioTotal(dto.getPrecioTotal());

        if (dto.getIdDestino() != null) {
            Destino destino = new Destino();
            destino.setIdDestino(dto.getIdDestino());
            paquete.setDestino(destino);
        }
        return paquete;
    }
}
