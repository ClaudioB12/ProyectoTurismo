package pe.edu.upeu.sistematurismocapachica.mappers;

import pe.edu.upeu.sistematurismocapachica.dtos.ActividadDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Actividad;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;

public class ActividadMapper {
    public static ActividadDto toDto(Actividad actividad) {
        ActividadDto dto = new ActividadDto();
        dto.setIdActividad(actividad.getIdActividad());
        dto.setNombre(actividad.getNombre());
        dto.setDescripcion(actividad.getDescripcion());
        dto.setPrecio(actividad.getPrecio());
        dto.setIdDestino(actividad.getDestino() != null ? actividad.getDestino().getIdDestino() : null);
        return dto;
    }

    public static Actividad toEntity(ActividadDto dto) {
        Actividad actividad = new Actividad();
        actividad.setIdActividad(dto.getIdActividad());
        actividad.setNombre(dto.getNombre());
        actividad.setDescripcion(dto.getDescripcion());
        actividad.setPrecio(dto.getPrecio());

        if (dto.getIdDestino() != null) {
            Destino destino = new Destino();
            destino.setIdDestino(dto.getIdDestino());
            actividad.setDestino(destino);
        }
        return actividad;
    }
}

