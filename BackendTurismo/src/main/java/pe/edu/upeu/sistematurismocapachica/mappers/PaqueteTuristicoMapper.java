package pe.edu.upeu.sistematurismocapachica.mappers;

import pe.edu.upeu.sistematurismocapachica.dtos.PaqueteTuristicoDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;
import pe.edu.upeu.sistematurismocapachica.modelo.PaqueteTuristico;

import java.util.List;
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
            dto.setActividades(paquete.getActividades()); // ✅ enviar objetos completos
        }

        if (paquete.getDestino() != null) {
            dto.setIdDestino(paquete.getDestino().getIdDestino());
            dto.setDestino(paquete.getDestino()); // ✅ enviar objeto completo
        }

        return dto;
    }

    public static PaqueteTuristico toEntity(PaqueteTuristicoDto dto,
                                            List<Actividad> todasLasActividades,
                                            List<Destino> todosLosDestinos) {
        PaqueteTuristico paquete = new PaqueteTuristico();
        paquete.setIdPaquete(dto.getIdPaquete());
        paquete.setNombre(dto.getNombre());
        paquete.setDescripcion(dto.getDescripcion());
        paquete.setPrecioTotal(dto.getPrecioTotal());

        if (dto.getActividadesIds() != null) {
            List<Actividad> actividadesSeleccionadas = todasLasActividades.stream()
                    .filter(act -> dto.getActividadesIds().contains(act.getIdActividad()))
                    .collect(Collectors.toList());
            paquete.setActividades(actividadesSeleccionadas);
        }

        if (dto.getIdDestino() != null) {
            todosLosDestinos.stream()
                    .filter(dest -> dest.getIdDestino().equals(dto.getIdDestino()))
                    .findFirst()
                    .ifPresent(paquete::setDestino);
        }

        return paquete;
    }
}