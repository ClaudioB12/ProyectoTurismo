package pe.edu.upeu.sistematurismocapachica.mappers;

import pe.edu.upeu.sistematurismocapachica.dtos.HospedajeDto;
import pe.edu.upeu.sistematurismocapachica.modelo.Destino;
import pe.edu.upeu.sistematurismocapachica.modelo.Hospedaje;

public class HospedajeMapper {

    public static HospedajeDto toDto(Hospedaje hospedaje) {
        HospedajeDto dto = new HospedajeDto();
        dto.setIdHospedaje(hospedaje.getIdHospedaje());
        dto.setNombre(hospedaje.getNombre());
        dto.setDescripcion(hospedaje.getDescripcion());
        dto.setPrecioPorNoche(hospedaje.getPrecioPorNoche());
        dto.setLatitud(hospedaje.getLatitud());
        dto.setLatitud(hospedaje.getLongitud());
        dto.setIdDestino(hospedaje.getDestino() != null ? hospedaje.getDestino().getIdDestino() : null);
        return dto;
    }

    public static Hospedaje toEntity(HospedajeDto dto) {
        Hospedaje hospedaje = new Hospedaje();
        hospedaje.setIdHospedaje(dto.getIdHospedaje());
        hospedaje.setNombre(dto.getNombre());
        hospedaje.setDescripcion(dto.getDescripcion());
        hospedaje.setPrecioPorNoche(dto.getPrecioPorNoche());
        hospedaje.setLatitud(dto.getLatitud());
        hospedaje.setLongitud(dto.getLongitud());

        if (dto.getIdDestino() != null) {
            Destino destino = new Destino();
            destino.setIdDestino(dto.getIdDestino());
            hospedaje.setDestino(destino);
        }

        return hospedaje;
    }
}
